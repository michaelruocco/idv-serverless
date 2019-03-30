package uk.co.mruoc.idv.awslambda.authorizer.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApiGatewayMethodArnParser {

    public ApiGatewayMethodArn parse(final String rawArn) {
        try {
            final String[] arnPartials = rawArn.split(ApiGatewayMethodArn.ARN_DELIMITER);
            final String[] apiPartials = arnPartials[5].split(ApiGatewayMethodArn.API_DELIMITER);
            return DefaultApiGatewayMethodArn.builder()
                    .region(arnPartials[3])
                    .accountId(arnPartials[4])
                    .apiId(apiPartials[0])
                    .stage(apiPartials[1])
                    .httpMethod(apiPartials[2])
                    .resource(extractResource(apiPartials))
                    .build();
        } catch (final ArrayIndexOutOfBoundsException e) {
            throw new InvalidApiGatewayArnException(rawArn, e);
        }
    }

    private static String extractResource(final String[] apiPartials) {
        return rebuildResourceId(apiPartials);
    }

    private static String rebuildResourceId(final String[] apiPartials) {
        final List<String> resourceElements = Arrays.asList(apiPartials)
                .subList(3, apiPartials.length);

        return String.join(ApiGatewayMethodArn.API_DELIMITER, resourceElements);
    }

    public static class InvalidApiGatewayArnException extends RuntimeException {

        public InvalidApiGatewayArnException(final String message, final Throwable cause) {
            super(message, cause);
        }

    }

}
