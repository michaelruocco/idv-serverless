package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationContextDao;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextObjectMapperSingleton;

public class VerificationContextDaoFactory {

    public static VerificationContextDao build(final String region, final String stage) {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();
        final String tableName = String.format("%s-verification-context", stage);
        return build(client, tableName);
    }

    public static VerificationContextDao build(final AmazonDynamoDB client, final String tableName) {
        final DynamoDB dynamoDB = new DynamoDB(client);
        final Table table = dynamoDB.getTable(tableName);
        return DynamoVerificationContextDao.builder()
                .table(table)
                .mapper(VerificationContextObjectMapperSingleton.get())
                .build();
    }

}
