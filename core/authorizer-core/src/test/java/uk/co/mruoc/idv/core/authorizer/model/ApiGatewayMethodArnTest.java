package uk.co.mruoc.idv.core.authorizer.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiGatewayMethodArnTest {

    @Test
    public void shouldReturnRegion() {
        final String region = "eu-west-1";

        final ApiGatewayMethodArn arn = DefaultApiGatewayMethodArn.builder()
                .region(region)
                .build();

        assertThat(arn.getRegion()).isEqualTo(region);
    }

    @Test
    public void shouldReturnAccountId() {
        final String accountId = "327122349051";

        final ApiGatewayMethodArn arn = DefaultApiGatewayMethodArn.builder()
                .accountId(accountId)
                .build();

        assertThat(arn.getAccountId()).isEqualTo(accountId);
    }

    @Test
    public void shouldReturnApiId() {
        final String apiId = "8tu67utdf7";

        final ApiGatewayMethodArn arn = DefaultApiGatewayMethodArn.builder()
                .apiId(apiId)
                .build();

        assertThat(arn.getApiId()).isEqualTo(apiId);
    }

    @Test
    public void shouldReturnStage() {
        final String stage = "dev";

        final ApiGatewayMethodArn arn = DefaultApiGatewayMethodArn.builder()
                .stage(stage)
                .build();

        assertThat(arn.getStage()).isEqualTo(stage);
    }

    @Test
    public void shouldReturnHttpMethod() {
        final String httpMethod = "GET";

        final ApiGatewayMethodArn arn = DefaultApiGatewayMethodArn.builder()
                .httpMethod(httpMethod)
                .build();

        assertThat(arn.getHttpMethod()).isEqualTo(httpMethod);
    }

    @Test
    public void shouldReturnResource() {
        final String resource = "verificationContexts/*";

        final ApiGatewayMethodArn arn = DefaultApiGatewayMethodArn.builder()
                .resource(resource)
                .build();

        assertThat(arn.getResource()).isEqualTo(resource);
    }

    @Test
    public void shouldReturnRawValue() {
        final String rawValue = "arn:aws:execute-api:eu-west-1:327122349051:8tu67utdf7/dev/GET/verificationContexts/*";

        final ApiGatewayMethodArn arn = DefaultApiGatewayMethodArn.builder()
                .region("eu-west-1")
                .accountId("327122349051")
                .apiId("8tu67utdf7")
                .stage("dev")
                .httpMethod("GET")
                .resource("verificationContexts/*")
                .build();

        assertThat(arn.getRawValue()).isEqualTo(rawValue);
    }

}
