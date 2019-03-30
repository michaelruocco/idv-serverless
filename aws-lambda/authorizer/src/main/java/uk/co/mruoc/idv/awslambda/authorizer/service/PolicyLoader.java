package uk.co.mruoc.idv.awslambda.authorizer.service;

import uk.co.mruoc.idv.awslambda.authorizer.model.ApiGatewayMethodArn;

public interface PolicyLoader {

    String load(final String principalId, final ApiGatewayMethodArn arn);


}
