package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationResult;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultsDao;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamoVerificationResultsDaoTest {

    private static final String TABLE_NAME = "verification-result";
    private static final JsonConverter CONVERTER = new VerificationContextJsonConverterFactory().build();

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private final VerificationTableFactory tableFactory = new VerificationTableFactory(TABLE_NAME);

    private VerificationResultsDao dao;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        tableFactory.createTable(client);

        dao = DynamoVerificationResultsDao.builder()
                .table(new DynamoDB(client).getTable(TABLE_NAME))
                .converter(CONVERTER)
                .build();
    }

    @Test
    public void shouldSaveResultsAndLoadById() {
        final VerificationMethodResults results = buildVerificationResults();
        dao.save(results);

        final Optional<VerificationMethodResults> loadedResults = dao.load(results.getContextId());

        assertThat(loadedResults.isPresent()).isTrue();
        assertThat(loadedResults.get().getContextId()).isEqualTo(results.getContextId());
        assertThat(loadedResults.get()).usingElementComparator(new VerificationMethodResultComparator())
                .containsExactlyElementsOf(results);
    }

    @Test
    public void shouldReturnEmptyOptionalForContextIdThatDoesNotHaveResults() {
        final UUID contextId = UUID.randomUUID();

        final Optional<VerificationMethodResults> loadedContext = dao.load(contextId);

        assertThat(loadedContext).isEmpty();
    }

    private static VerificationMethodResults buildVerificationResults() {
        final UUID contextId = UUID.randomUUID();
        final UUID verificationId = UUID.randomUUID();
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName("sequence")
                .methodName("method")
                .result(VerificationResult.SUCCESS)
                .verificationId(verificationId)
                .timestamp(Instant.now())
                .build();
        return VerificationMethodResults.builder()
                .id(UUID.randomUUID())
                .contextId(contextId)
                .results(Collections.singleton(result))
                .build();
    }

    private static class VerificationMethodResultComparator implements Comparator<VerificationMethodResult> {

        @Override
        public int compare(final VerificationMethodResult r1, final VerificationMethodResult r2) {
            return r1.getContextId().compareTo(r2.getContextId()) +
                    r1.getVerificationId().compareTo(r2.getVerificationId()) +
                    r1.getMethodName().compareTo(r2.getMethodName()) +
                    r1.getSequenceName().compareTo(r2.getSequenceName()) +
                    r1.getTimestamp().compareTo(r2.getTimestamp()) +
                    r1.getResult().compareTo(r2.getResult());
        }

    }

}
