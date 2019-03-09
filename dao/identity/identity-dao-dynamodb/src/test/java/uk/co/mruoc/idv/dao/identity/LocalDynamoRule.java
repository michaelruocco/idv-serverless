package uk.co.mruoc.idv.dao.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import org.junit.rules.ExternalResource;

public class LocalDynamoRule extends ExternalResource {

    private AmazonDynamoDB client;

    public AmazonDynamoDB getClient() {
        return client;
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
