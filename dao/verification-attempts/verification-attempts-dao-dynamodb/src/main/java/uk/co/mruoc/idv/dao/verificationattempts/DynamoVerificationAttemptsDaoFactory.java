package uk.co.mruoc.idv.dao.verificationattempts;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;
import uk.co.mruoc.idv.json.JsonConverter;

public class DynamoVerificationAttemptsDaoFactory implements VerificationAttemptsDaoFactory {

    private static final String IDV_ID_TABLE_NAME_FORMAT = "%s-idv-id-verification-attempt";
    private static final String CONTEXT_ID_TABLE_NAME_FORMAT = "%s-context-id-verification-attempt";

    private final Table idvIdAttemptsTable;
    private final Table contextIdAttemptsTable;
    private final JsonConverter converter;

    public DynamoVerificationAttemptsDaoFactory(final DynamoEnvironment environment, final JsonConverter converter) {
        this(toIdvIdTable(environment), toContextIdTable(environment), converter);
    }

    public DynamoVerificationAttemptsDaoFactory(final Table idvIdAttemptsTable, final Table contextIdAttemptsTable, final JsonConverter converter) {
        this.idvIdAttemptsTable = idvIdAttemptsTable;
        this.contextIdAttemptsTable = contextIdAttemptsTable;
        this.converter = converter;
    }

    @Override
    public VerificationAttemptsDao build() {
        return DynamoVerificationAttemptsDao.builder()
                .idvIdAttemptsTable(idvIdAttemptsTable)
                .contextIdAttemptsTable(contextIdAttemptsTable)
                .converter(converter)
                .build();
    }

    private static Table toIdvIdTable(final DynamoEnvironment environment) {
        return toTable(environment, toIdvIdTableName(environment.getStage()));
    }

    private static String toIdvIdTableName(final String stage) {
        return String.format(IDV_ID_TABLE_NAME_FORMAT, stage);
    }

    private static Table toContextIdTable(final DynamoEnvironment environment) {
        return toTable(environment, toContextIdTableName(environment.getStage()));
    }

    private static String toContextIdTableName(final String stage) {
        return String.format(CONTEXT_ID_TABLE_NAME_FORMAT, stage);
    }

    private static Table toTable(final DynamoEnvironment environment, final String tableName) {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(environment.getRegion())
                .build();
        final DynamoDB dynamoDB = new DynamoDB(client);
        return dynamoDB.getTable(tableName);
    }

}
