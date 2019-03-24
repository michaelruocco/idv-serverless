package uk.co.mruoc.idv.awslambda;

import uk.co.mruoc.idv.dao.dynamodb.DynamoEnvironment;

public class Environment implements DynamoEnvironment {

    private final String region = System.getenv("REGION");
    private final String stage = System.getenv("STAGE");

    public String getRegion() {
        return region;
    }

    public String getStage() {
        return stage;
    }

}
