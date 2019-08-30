package uk.co.mruoc.idv.dao.verificationattempts;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationattempts.VerificationAttemptsJsonConverterFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamoVerificationAttemptsDaoTest {

    private static final String IDV_ID_TABLE_NAME = "idv-id-verification-attempt";
    private static final String CONTEXT_ID_TABLE_NAME = "context-id-verification-attempt";
    private static final JsonConverter CONVERTER = new VerificationAttemptsJsonConverterFactory().build();

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private final VerificationAttemptTableFactory idvIdTableFactory = new VerificationAttemptTableFactory("idvId", IDV_ID_TABLE_NAME);
    private final VerificationAttemptTableFactory contextIdTableFactory = new VerificationAttemptTableFactory("contextId", CONTEXT_ID_TABLE_NAME);

    private VerificationAttemptsDao dao;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        final DynamoDB dynamoDB = localDynamoRule.getDynamoDb();

        idvIdTableFactory.createTable(client);
        contextIdTableFactory.createTable(client);

        dao = DynamoVerificationAttemptsDao.builder()
                .idvIdAttemptsTable(dynamoDB.getTable(IDV_ID_TABLE_NAME))
                .contextIdAttemptsTable(dynamoDB.getTable(CONTEXT_ID_TABLE_NAME))
                .converter(CONVERTER)
                .build();
    }

    @Test
    public void shouldSaveAttemptsAndLoadByIdvId() {
        final UUID idvId = UUID.randomUUID();
        final VerificationAttempts attempts = buildVerificationAttempts(
                buildAttempt(idvId),
                buildAttempt(idvId)
        );
        dao.save(attempts);

        final Optional<VerificationAttempts> loadedAttempts = dao.loadByIdvId(attempts.getIdvId());

        assertThat(loadedAttempts.isPresent()).isTrue();
        assertThat(loadedAttempts.get())
                .usingElementComparator(new VerificationAttemptComparator())
                .containsExactlyInAnyOrderElementsOf(attempts);
    }

    @Test
    public void shouldSaveAttemptsAndLoadByContextId() {
        final UUID idvId = UUID.randomUUID();
        final VerificationAttempt attempt1 = buildAttempt(idvId);
        final VerificationAttempts attempts = buildVerificationAttempts(
                attempt1,
                buildAttempt(idvId)
        );
        dao.save(attempts);

        final Optional<VerificationAttempts> loadedAttempts = dao.loadByContextId(attempt1.getContextId());

        assertThat(loadedAttempts.isPresent()).isTrue();
        assertThat(loadedAttempts.get())
                .usingElementComparator(new VerificationAttemptComparator())
                .containsExactly(attempt1);
    }

    @Test
    public void shouldReturnEmptyOptionalForContextIdThatDoesNotHaveResults() {
        final UUID contextId = UUID.randomUUID();

        final Optional<VerificationAttempts> loadedAttempts = dao.loadByIdvId(contextId);

        assertThat(loadedAttempts).isEmpty();
    }

    @Test
    public void shouldAddAttemptsToExistingAttemptsWhenSaving() {
        final UUID contextId = UUID.randomUUID();
        final UUID idvId = UUID.randomUUID();
        final VerificationAttempts attempts1 = buildVerificationAttempts(
                buildAttempt(idvId, contextId)
        );
        final VerificationAttempts attempts2 = buildVerificationAttempts(
                buildAttempt(idvId, contextId)
        );
        dao.save(attempts1);
        dao.save(attempts2);

        final Optional<VerificationAttempts> loadedAttempts = dao.loadByContextId(contextId);

        assertThat(loadedAttempts.isPresent()).isTrue();
        assertThat(loadedAttempts.get())
                .usingElementComparator(new VerificationAttemptComparator())
                .containsExactlyElementsOf(merge(attempts1, attempts2));
    }

    private static VerificationAttempts buildVerificationAttempts(final VerificationAttempt... attempts) {
        final UUID idvId = UUID.randomUUID();
        final UUID lockoutStateId = UUID.randomUUID();
        return VerificationAttempts.builder()
                .idvId(idvId)
                .lockoutStateId(lockoutStateId)
                .attempts(Arrays.asList(attempts))
                .build();
    }

    private static VerificationAttempt buildAttempt(final UUID idvId) {
        final UUID contextId = UUID.randomUUID();
        return buildAttempt(idvId, contextId);
    }

    private static VerificationAttempt buildAttempt(final UUID idvId, final UUID contextId) {
        final UUID verificationId = UUID.randomUUID();
        return VerificationAttempt.builder()
                .contextId(contextId)
                .channelId("channelId")
                .methodName("method")
                .alias(new IdvIdAlias(idvId))
                .activityType("activityType")
                .successful(true)
                .timestamp(Instant.now())
                .verificationId(verificationId)
                .build();
    }

    private static Collection<VerificationAttempt> merge(final VerificationAttempts attempts1, final VerificationAttempts attempts2) {
        final Collection<VerificationAttempt> allAttempts = new ArrayList<>(attempts1.toCollection());
        allAttempts.addAll(attempts2.toCollection());
        return Collections.unmodifiableCollection(allAttempts);
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
                    a1.getContextId().compareTo(a2.getContextId()) +
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
