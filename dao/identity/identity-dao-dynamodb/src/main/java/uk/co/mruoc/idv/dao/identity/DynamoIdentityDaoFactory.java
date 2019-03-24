package uk.co.mruoc.idv.dao.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityDaoFactory;
import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;

public class DynamoIdentityDaoFactory implements IdentityDaoFactory {

    private static final String TABLE_NAME_FORMAT = "%s-identity";

    private final DynamoDB client;
    private final String tableName;

    public DynamoIdentityDaoFactory(final DynamoEnvironment environment) {
        this(toClient(environment), toTableName(environment));
    }

    public DynamoIdentityDaoFactory(final DynamoDB client, final String tableName) {
        this.client = client;
        this.tableName = tableName;
    }

    @Override
    public IdentityDao build() {
        return new DynamoIdentityDao(client, tableName);
    }

    private static DynamoDB toClient(final DynamoEnvironment environment) {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(environment.getRegion())
                .build();
        return new DynamoDB(client);
    }

    private static String toTableName(final DynamoEnvironment environment) {
        return String.format(TABLE_NAME_FORMAT, environment.getStage());
    }

}
