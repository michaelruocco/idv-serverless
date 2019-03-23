package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.json.identity.IdvIdentityModule;
import uk.co.mruoc.idv.json.verificationcontext.IdvVerificationContextModule;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@Slf4j
public class DynamoVerificationContextDaoTest {

    private static final String TABLE_NAME = "verification-context";

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private VerificationContextDao dao;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        createTable(client);

        dao = DynamoVerificationContextDao.builder()
                .table(new DynamoDB(client).getTable(TABLE_NAME))
                .mapper(buildMapper())
                .build();
    }

    @Test
    public void shouldSaveContextAndLoadById() {
        final VerificationContext context = buildVerificationContext();
        dao.save(context);

        final Optional<VerificationContext> loadedContext = dao.load(context.getId());

        assertThat(loadedContext.isPresent()).isTrue();
        assertThat(loadedContext.get()).isEqualToComparingFieldByFieldRecursively(context);
    }

    @Test
    public void shouldReturnEmptyOptionalForContextIdThatDoesNotExist() {
        final UUID id = UUID.randomUUID();

        final Optional<VerificationContext> loadedContext = dao.load(id);

        assertThat(loadedContext).isEmpty();
    }

    @Test
    public void shouldThrowExceptionIfMapperFailsToWriteObjectToJson() throws JsonProcessingException {
        final Table table = mock(Table.class);
        final ObjectMapper mapper = mock(ObjectMapper.class);
        final VerificationContextDao daoWithMocks = DynamoVerificationContextDao.builder()
                .table(table)
                .mapper(mapper)
                .build();

        final VerificationContext context = buildVerificationContext();
        doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(context);

        final Throwable cause = catchThrowable(() -> daoWithMocks.save(context));

        assertThat(cause).isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(JsonProcessingException.class);
    }

    @Test
    public void shouldReturnThrowExceptionIfMapperFailsToReadObjectFromJson() throws IOException {
        final Table table = mock(Table.class);
        final ObjectMapper mapper = mock(ObjectMapper.class);
        final VerificationContextDao daoWithMocks = DynamoVerificationContextDao.builder()
                .table(table)
                .mapper(mapper)
                .build();
        final UUID id = UUID.randomUUID();
        final Item item = mock(Item.class);
        final String jsonBody = "{}";
        given(table.getItem("id", id.toString())).willReturn(item);
        given(item.getJSON("body")).willReturn(jsonBody);
        doThrow(IOException.class).when(mapper).readValue(jsonBody, VerificationContext.class);

        final Throwable cause = catchThrowable(() -> daoWithMocks.load(id));

        assertThat(cause).isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(IOException.class);
    }

    private static void createTable(final AmazonDynamoDB client) throws InterruptedException {
        final List<KeySchemaElement> keySchema = Collections.singletonList(new KeySchemaElement()
                .withAttributeName("id")
                .withKeyType(KeyType.HASH));

        final List<AttributeDefinition> attributeDefinitions = Collections.singletonList(new AttributeDefinition()
                .withAttributeName("id")
                .withAttributeType("S"));

        final ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
                .withReadCapacityUnits(1L)
                .withWriteCapacityUnits(1L);

        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(TABLE_NAME)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(provisionedThroughput);

        log.info("creating table {}", TABLE_NAME);
        TableUtils.createTableIfNotExists(client, request);
        log.info("waiting until table {} is active", TABLE_NAME);
        TableUtils.waitUntilActive(client, TABLE_NAME, 60000, 5000);
        log.info("table {} is active", TABLE_NAME);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvVerificationContextModule());
        mapper.registerModule(new IdvIdentityModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new MoneyModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    private static VerificationContext buildVerificationContext() {
        final UUID id = UUID.randomUUID();
        final Collection<MobileNumber> mobileNumbers = Collections.singleton(MobileNumber.builder().id(UUID.fromString("4b21d79e-43b5-43bb-97c9-6979553e9a16")).masked("*******123").build());
        final Passcode passcode = Passcode.builder().duration(15000).length(8).attempts(3).build();
        final Alias providedAlias = new TokenizedCreditCardNumberAlias("1234567890123456");
        return VerificationContext.builder()
                .id(id)
                .channel(new DefaultChannel("CHANNEL_ID"))
                .activity(new LoginActivity(Instant.now()))
                .providedAlias(providedAlias)
                .identity(Identity.withAliases(new IdvIdAlias(), providedAlias))
                .created(Instant.now())
                .expiry(Instant.now().plus(Duration.ofMinutes(5)))
                .eligibleMethods(Collections.singleton(new VerificationMethodSequence(new OtpSmsVerificationMethod(300000, passcode, mobileNumbers))))
                .build();
    }

}
