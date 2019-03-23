package uk.co.mruoc.idv.dao.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedDebitCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class DynamoIdentityDaoTest {

    private static final String TABLE_NAME = "identity";

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private IdentityDao dao;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        createTable(client);

        dao = new DynamoIdentityDao(new DynamoDB(client), TABLE_NAME);
    }

    @Test
    public void shouldSaveIdentityAndLoadByAnyAlias() {
        final Identity identity = Identity.withAliases(
                new IdvIdAlias(),
                new TokenizedCreditCardNumberAlias("1234567890123456"),
                new TokenizedDebitCardNumberAlias("1111111111111111")
        );

        dao.save(identity);

        identity.getAliases().forEach(alias -> assertThat(dao.load(alias).get().getAliases()).containsExactlyInAnyOrderElementsOf(identity.getAliases()));
    }

    @Test
    public void identityShouldRemainSameIfSameIdentityIsSavedTwice() {
        final Identity identity = Identity.withAliases(
                new IdvIdAlias(),
                new TokenizedDebitCardNumberAlias("1234567890123456"),
                new TokenizedCreditCardNumberAlias("4929123456789012")
        );

        dao.save(identity);
        identity.getAliases().forEach(alias -> assertThat(dao.load(alias).get().getAliases()).containsExactlyInAnyOrderElementsOf(identity.getAliases()));

        dao.save(identity);
        identity.getAliases().forEach(alias -> assertThat(dao.load(alias).get().getAliases()).containsExactlyInAnyOrderElementsOf(identity.getAliases()));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenIdentityNotFound() {
        Optional<Identity> result = dao.load(new IdvIdAlias());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void shouldDeleteIdentity() {
        final Identity identity = Identity.withAliases(
                new IdvIdAlias(),
                new TokenizedCreditCardNumberAlias("1234567890123456"),
                new TokenizedDebitCardNumberAlias("4929123456789012")
        );

        dao.save(identity);
        identity.getAliases().forEach(alias -> assertThat(dao.load(alias).get().getAliases()).containsExactlyInAnyOrderElementsOf(identity.getAliases()));

        dao.delete(identity);
        identity.getAliases().forEach(alias -> assertThat(dao.load(alias)).isEmpty());
    }

    private static void createTable(final AmazonDynamoDB client) throws InterruptedException {
        final List<KeySchemaElement> keySchema = Collections.singletonList(new KeySchemaElement()
                .withAttributeName("alias")
                .withKeyType(KeyType.HASH));

        final List<AttributeDefinition> attributeDefinitions = Collections.singletonList(new AttributeDefinition()
                .withAttributeName("alias")
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

}
