package uk.co.mruoc.idv.dao.verificationattempts;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import org.junit.rules.ExternalResource;

public class LocalDynamoRule extends ExternalResource {

    private AmazonDynamoDB client;

    public AmazonDynamoDB getClient() {
        return client;
    }

    public DynamoDB getDynamoDb() {
        return new DynamoDB(client);
    }

    @Override
    protected void before() {
        this.client = DynamoDBEmbedded.create().amazonDynamoDB();
    }

    @Override
    protected void after() {
        client.shutdown();
    }

}
