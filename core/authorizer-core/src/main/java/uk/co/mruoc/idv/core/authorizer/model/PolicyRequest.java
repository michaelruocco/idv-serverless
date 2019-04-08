package uk.co.mruoc.idv.core.authorizer.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicyRequest {

    private final String principalId;
    private final ApiGatewayMethodArn methodArn;

}
