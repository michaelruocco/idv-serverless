package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.RequestValidator;

import java.util.Map;

@Slf4j
public class GetIdentityRequestValidator implements RequestValidator {

    @Override
    public boolean validate(final APIGatewayProxyRequestEvent requestEvent) {
        log.debug("validating request {}", requestEvent);
        if (isValid(requestEvent)) {
            return true;
        }

        throw new IdentityRequestInvalidException();
    }

    private static boolean isValid(final APIGatewayProxyRequestEvent requestEvent) {
        if (hasIdPathParameter(requestEvent)) {
            log.debug("id path parameter provided, request is valid");
            return true;
        }

        if (hasAliasTypeAndValueQueryStringParameters(requestEvent)) {
            log.debug("aliasType and aliasValue query string parameters provided, request is valid");
            return true;
        }

        return false;
    }

    private static boolean hasIdPathParameter(final APIGatewayProxyRequestEvent requestEvent) {
        final Map<String, String> pathParameters = requestEvent.getPathParameters();
        return pathParameters != null && pathParameters.containsKey("id");
    }

    private static boolean hasAliasTypeAndValueQueryStringParameters(final APIGatewayProxyRequestEvent requestEvent) {
        final Map<String, String> queryStringParameters = requestEvent.getQueryStringParameters();
        return queryStringParameters != null &&
                queryStringParameters.containsKey("aliasType") &&
                queryStringParameters.containsKey("aliasValue");
    }

    public static class IdentityRequestInvalidException extends RuntimeException {

        // intentionally blank

    }

}
