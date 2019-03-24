package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;
import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;

public class DynamoVerificationContextDaoFactory implements VerificationContextDaoFactory {

    private static final String TABLE_NAME_FORMAT = "%s-verification-context";

    private final Table table;
    private final ObjectMapper mapper;

    public DynamoVerificationContextDaoFactory(final DynamoEnvironment environment, final ObjectMapper mapper) {
        this(toTable(environment), mapper);
    }

    public DynamoVerificationContextDaoFactory(final Table table, final ObjectMapper mapper) {
        this.table = table;
        this.mapper = mapper;
    }

    @Override
    public VerificationContextDao build() {
        return DynamoVerificationContextDao.builder()
                .table(table)
                .mapper(mapper)
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
