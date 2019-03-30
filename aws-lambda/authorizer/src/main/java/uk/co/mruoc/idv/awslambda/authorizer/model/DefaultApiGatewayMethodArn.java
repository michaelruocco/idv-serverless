package uk.co.mruoc.idv.awslambda.authorizer.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultApiGatewayMethodArn implements ApiGatewayMethodArn {

    private final String region;
    private final String accountId;

    private final String apiId;
    private final String stage;
    private final String httpMethod;
    private final String resource;

    @Override
    public String getRawValue() {
        final String apiDetails = buildApiDetails();
        return buildRawValue(apiDetails);
    }

    private String buildApiDetails() {
        return String.join(API_DELIMITER, apiId, stage, httpMethod, resource);
    }

    private String buildRawValue(final String apiDetails) {
        return String.join(ARN_DELIMITER, PREFIX, region, accountId, apiDetails);
    }

}
