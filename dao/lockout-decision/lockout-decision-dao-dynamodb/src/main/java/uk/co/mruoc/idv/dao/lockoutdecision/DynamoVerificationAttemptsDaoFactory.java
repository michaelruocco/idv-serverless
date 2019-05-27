package uk.co.mruoc.idv.dao.lockoutdecision;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;
import uk.co.mruoc.idv.json.JsonConverter;

public class DynamoVerificationAttemptsDaoFactory implements VerificationAttemptsDaoFactory {

    private static final String TABLE_NAME_FORMAT = "%s-verification-attempt";

    private final Table table;
    private final JsonConverter converter;

    public DynamoVerificationAttemptsDaoFactory(final DynamoEnvironment environment, final JsonConverter converter) {
        this(toTable(environment), converter);
    }

    public DynamoVerificationAttemptsDaoFactory(final Table table, final JsonConverter converter) {
        this.table = table;
        this.converter = converter;
    }

    @Override
    public VerificationAttemptsDao build() {
        return DynamoVerificationAttemptsDao.builder()
                .table(table)
                .converter(converter)
                .build();
    }

    private static Table toTable(final DynamoEnvironment environment) {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(environment.getRegion())
                .build();
        final DynamoDB dynamoDB = new DynamoDB(client);
        final String tableName = toTableName(environment.getStage());
        return dynamoDB.getTable(tableName);
    }

    private static String toTableName(final String stage) {
        return String.format(TABLE_NAME_FORMAT, stage);
    }

}
