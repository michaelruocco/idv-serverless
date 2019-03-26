package uk.co.mruoc.idv.awslambda;

import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;
import uk.co.mruoc.idv.events.sns.SnsEventEnvironment;

public class Environment implements DynamoEnvironment, SnsEventEnvironment {

    private final String region = System.getenv("REGION");
    private final String stage = System.getenv("STAGE");
    private final String eventTopicArn = System.getenv("EVENT_TOPIC_ARN");

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String getStage() {
        return stage;
    }

    @Override
    public String getEventTopicArn() {
        return eventTopicArn;
    }

}
