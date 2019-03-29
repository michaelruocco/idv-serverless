package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.dao.dynamodb.DefaultDynamoEnvironment;
import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;
import uk.co.mruoc.idv.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class DynamoVerificationContextDaoFactoryTest {

    private static final String TABLE_NAME = "verification-context";

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private final DynamoEnvironment environment = new DefaultDynamoEnvironment("region", "stage");

    private DynamoVerificationContextDaoFactory factory;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        final VerificationContextTableFactory tableFactory = new VerificationContextTableFactory(TABLE_NAME);
        tableFactory.createTable(client);

        final JsonConverter converter = mock(JsonConverter.class);
        factory = new DynamoVerificationContextDaoFactory(environment, converter);
    }

    @Test
    public void shouldBuildDao() {
        final VerificationContextDao dao = factory.build();

        assertThat(dao).isInstanceOf(DynamoVerificationContextDao.class);
    }

}
