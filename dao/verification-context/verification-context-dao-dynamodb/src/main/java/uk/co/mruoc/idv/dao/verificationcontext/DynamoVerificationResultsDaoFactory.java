package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDao;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDaoFactory;
import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;
import uk.co.mruoc.idv.json.JsonConverter;

public class DynamoVerificationResultsDaoFactory implements VerificationResultsDaoFactory {

    private static final String TABLE_NAME_FORMAT = "%s-verification-result";

    private final Table table;
    private final JsonConverter converter;

    public DynamoVerificationResultsDaoFactory(final DynamoEnvironment environment, final JsonConverter converter) {
        this(toTable(environment), converter);
    }

    public DynamoVerificationResultsDaoFactory(final Table table, final JsonConverter converter) {
        this.table = table;
        this.converter = converter;
    }

    @Override
    public VerificationResultsDao build() {
        return DynamoVerificationResultsDao.builder()
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
