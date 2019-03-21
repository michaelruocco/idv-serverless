package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.dao.identity.DynamoIdentityDao;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationContextDao;
import uk.co.mruoc.idv.jsonapi.identity.IdvIdentityModule;
import uk.co.mruoc.idv.jsonapi.verificationcontext.IdvVerificationContextModule;

public class VerificationContextDaoFactory {

    public static VerificationContextDao build(final String region, final String stage) {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();
        final String tableName = String.format("%s-verification-context", stage);
        return build(client, tableName);
    }

    public static VerificationContextDao build(final AmazonDynamoDB client, final String tableName) {
        final Table table = new DynamoDB(client).getTable(tableName);
        return new DynamoVerificationContextDao(table, buildMapper());
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvVerificationContextModule());
        mapper.registerModule(new IdvIdentityModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new MoneyModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
