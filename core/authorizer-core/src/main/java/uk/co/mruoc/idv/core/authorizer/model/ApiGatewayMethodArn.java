package uk.co.mruoc.idv.core.authorizer.model;

public interface ApiGatewayMethodArn {

    String API_DELIMITER = "/";
    String ARN_DELIMITER = ":";
    String PREFIX = "arn:aws:execute-api";

    String getRawValue();

    String getRegion();

    String getAccountId();

    String getApiId();

    String getStage();

    String getHttpMethod();

    String getResource();

}
