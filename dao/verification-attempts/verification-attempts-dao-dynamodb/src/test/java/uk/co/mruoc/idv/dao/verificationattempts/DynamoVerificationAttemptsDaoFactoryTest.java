package uk.co.mruoc.idv.dao.verificationattempts;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.dao.dynamodb.DefaultDynamoEnvironment;
import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;
import uk.co.mruoc.idv.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class DynamoVerificationAttemptsDaoFactoryTest {

    private static final String TABLE_NAME = "verification-attempt";

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private final DynamoEnvironment environment = new DefaultDynamoEnvironment("region", "stage");

    private DynamoVerificationAttemptsDaoFactory factory;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        final VerificationAttemptTableFactory tableFactory = new VerificationAttemptTableFactory(TABLE_NAME);
        tableFactory.createTable(client);

        final JsonConverter converter = mock(JsonConverter.class);
        factory = new DynamoVerificationAttemptsDaoFactory(environment, converter);
    }

    @Test
    public void shouldBuildDao() {
        final VerificationAttemptsDao dao = factory.build();

        assertThat(dao).isInstanceOf(DynamoVerificationAttemptsDao.class);
    }

}
