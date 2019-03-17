package uk.co.mruoc.idv.awslambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

public interface RequestValidator {

    boolean validate(final APIGatewayProxyRequestEvent requestEvent);

}
