package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamoVerificationContextDaoTest {

    private static final String TABLE_NAME = "verification-context";

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private final VerificationTableFactory tableFactory = new VerificationTableFactory(TABLE_NAME);

    private VerificationContextDao dao;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        tableFactory.createTable(client);

        dao = DynamoVerificationContextDao.builder()
                .table(new DynamoDB(client).getTable(TABLE_NAME))
                .converter(new VerificationContextJsonConverterFactory().build())
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

    private static VerificationContext buildVerificationContext() {
        final UUID id = UUID.randomUUID();
        final Collection<MobileNumber> mobileNumbers = Collections.singleton(MobileNumber.builder().id(UUID.fromString("4b21d79e-43b5-43bb-97c9-6979553e9a16")).masked("*******123").build());
        final Passcode passcode = Passcode.builder()
                .duration(15000)
                .length(8)
                .maxAttempts(3)
                .build();
        final Alias providedAlias = new TokenizedCreditCardNumberAlias("1234567890123456");
        return VerificationContext.builder()
                .id(id)
                .channel(new DefaultChannel("CHANNEL_ID"))
                .activity(new LoginActivity(Instant.now()))
                .providedAlias(providedAlias)
                .identity(Identity.withAliases(new IdvIdAlias(), providedAlias))
                .created(Instant.now())
                .expiry(Instant.now().plus(Duration.ofMinutes(5)))
                .sequences(Collections.singleton(new VerificationMethodSequence(new OtpSmsVerificationMethod(300000, passcode, mobileNumbers))))
                .build();
    }

}
