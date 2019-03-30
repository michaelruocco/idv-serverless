package uk.co.mruoc.idv.awslambda.authorizer.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.awslambda.authorizer.model.ApiGatewayMethodArn;

@Getter
@Builder
public class PolicyRequest {

    private final String principalId;
    private final ApiGatewayMethodArn methodArn;

}
