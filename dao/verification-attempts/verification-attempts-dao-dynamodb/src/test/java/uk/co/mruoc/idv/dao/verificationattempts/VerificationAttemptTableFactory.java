package uk.co.mruoc.idv.dao.verificationattempts;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class VerificationAttemptTableFactory {

    private final String tableName;

    public VerificationAttemptTableFactory(final String tableName) {
        this.tableName = tableName;
    }

    public void createTable(final AmazonDynamoDB client) throws InterruptedException {
        final List<KeySchemaElement> keySchema = Collections.singletonList(new KeySchemaElement()
                .withAttributeName("idvId")
                .withKeyType(KeyType.HASH));

        final List<AttributeDefinition> attributeDefinitions = Collections.singletonList(new AttributeDefinition()
                .withAttributeName("idvId")
                .withAttributeType("S"));

        final ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
                .withReadCapacityUnits(1L)
                .withWriteCapacityUnits(1L);

        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(provisionedThroughput);

        log.info("creating table {}", tableName);
        TableUtils.createTableIfNotExists(client, request);
        log.info("waiting until table {} is active", tableName);
        TableUtils.waitUntilActive(client, tableName, 60000, 5000);
        log.info("table {} is active", tableName);
    }

}
