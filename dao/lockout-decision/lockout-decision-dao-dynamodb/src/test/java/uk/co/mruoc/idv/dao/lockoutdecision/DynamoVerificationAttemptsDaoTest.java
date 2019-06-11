package uk.co.mruoc.idv.dao.lockoutdecision;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.lockoutdecision.LockoutDecisionJsonConverterFactory;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamoVerificationAttemptsDaoTest {

    private static final String TABLE_NAME = "verification-attempt";
    private static final JsonConverter CONVERTER = new LockoutDecisionJsonConverterFactory().build();

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private final VerificationAttemptTableFactory tableFactory = new VerificationAttemptTableFactory(TABLE_NAME);

    private VerificationAttemptsDao dao;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        tableFactory.createTable(client);

        dao = DynamoVerificationAttemptsDao.builder()
                .table(new DynamoDB(client).getTable(TABLE_NAME))
                .converter(CONVERTER)
                .build();
    }

    @Test
    public void shouldSaveResultsAndLoadById() {
        final VerificationAttempts attempts = buildVerificationAttempts();
        dao.save(attempts);

        final Optional<VerificationAttempts> loadedAttempts = dao.loadByIdvId(attempts.getIdvId());

        assertThat(loadedAttempts.isPresent()).isTrue();
        assertThat(loadedAttempts.get()).usingElementComparator(new VerificationAttemptComparator())
                .containsExactlyElementsOf(attempts);
    }

    @Test
    public void shouldReturnEmptyOptionalForContextIdThatDoesNotHaveResults() {
        final UUID contextId = UUID.randomUUID();

        final Optional<VerificationAttempts> loadedAttempts = dao.loadByIdvId(contextId);

        assertThat(loadedAttempts).isEmpty();
    }

    private static VerificationAttempts buildVerificationAttempts() {
        final UUID idvId = UUID.randomUUID();
        final UUID lockoutStateId = UUID.randomUUID();
        final VerificationAttempt attempt = VerificationAttempt.builder()
                .channelId("channelId")
                .methodName("method")
                .alias(new IdvIdAlias())
                .activityType("activityType")
                .result("result")
                .timestamp(Instant.now())
                .build();
        return VerificationAttempts.builder()
                .idvId(idvId)
                .lockoutStateId(lockoutStateId)
                .attempts(Collections.singleton(attempt))
                .build();
    }

    private static class VerificationAttemptComparator implements Comparator<VerificationAttempt> {

        private final AliasComparator aliasComparator = new AliasComparator();

        @Override
        public int compare(final VerificationAttempt a1, final VerificationAttempt a2) {
            return a1.getActivityType().compareTo(a2.getActivityType()) +
                    a1.getChannelId().compareTo(a2.getChannelId()) +
                    a1.getTimestamp().compareTo(a2.getTimestamp()) +
                    Boolean.compare(a1.isSuccessful(), a2.isSuccessful()) +
                    a1.getAliasTypeName().compareTo(a2.getAliasTypeName()) +
                    aliasComparator.compare(a1.getAlias(), a2.getAlias());
        }

    }

    private static class AliasComparator implements Comparator<Alias> {

        @Override
        public int compare(final Alias a1, final Alias a2) {
            return a1.getValue().compareTo(a2.getValue()) +
                    a1.getFormat().compareTo(a2.getFormat()) +
                    a1.getTypeName().compareTo(a2.getTypeName());
        }

    }

}
