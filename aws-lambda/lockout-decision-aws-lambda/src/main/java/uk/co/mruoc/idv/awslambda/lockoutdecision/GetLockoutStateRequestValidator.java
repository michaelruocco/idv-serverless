package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.RequestValidator;

import java.util.Map;

@Slf4j
public class GetLockoutStateRequestValidator implements RequestValidator {

    @Override
    public boolean validate(final APIGatewayProxyRequestEvent requestEvent) {
        log.debug("validating request {}", requestEvent);
        if (isValid(requestEvent)) {
            return true;
        }

        throw new InvalidLockoutStateRequestException();
    }

    private static boolean isValid(final APIGatewayProxyRequestEvent requestEvent) {
        if (hasRequiredQueryStringParameters(requestEvent)) {
            log.debug("required query string parameters provided, request is valid");
            return true;
        }

        return false;
    }

    private static boolean hasRequiredQueryStringParameters(final APIGatewayProxyRequestEvent requestEvent) {
        final Map<String, String> parameters = requestEvent.getQueryStringParameters();
        return parameters != null &&
                parameters.containsKey("aliasType") &&
                parameters.containsKey("aliasValue") &&
                parameters.containsKey("channelId") &&
                parameters.containsKey("activityType");
    }

    public static class InvalidLockoutStateRequestException extends RuntimeException {

        // intentionally blank

    }

}
