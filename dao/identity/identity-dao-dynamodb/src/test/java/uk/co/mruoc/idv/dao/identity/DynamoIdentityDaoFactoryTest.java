package uk.co.mruoc.idv.dao.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityDaoFactory;
import uk.co.mruoc.idv.dao.dynamodb.DefaultDynamoEnvironment;
import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamoIdentityDaoFactoryTest {

    private static final String TABLE_NAME = "identity";

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private final DynamoEnvironment environment = new DefaultDynamoEnvironment("region", "stage");

    private IdentityDaoFactory factory;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        final IdentityTableFactory tableFactory = new IdentityTableFactory(TABLE_NAME);
        tableFactory.createTable(client);

        factory = new DynamoIdentityDaoFactory(environment);
    }

    @Test
    public void shouldBuildDao() {
        final IdentityDao dao = factory.build();

        assertThat(dao).isInstanceOf(DynamoIdentityDao.class);
    }

}
