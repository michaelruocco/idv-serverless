package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.dao.identity.DynamoIdentityDao;

public class IdentityDaoFactory {

    public static IdentityDao build(final String region, final String stage) {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();
        final String tableName = String.format("%s-identity", stage);
        return build(client, tableName);
    }

    public static IdentityDao build(final AmazonDynamoDB client, final String tableName) {
        return new DynamoIdentityDao(new DynamoDB(client), tableName);
    }

}
