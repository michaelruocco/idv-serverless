package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.RequestValidator;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class GetIdentityRequestValidator implements RequestValidator {

    @Override
    public Optional<JsonApiErrorDocument> validate(final APIGatewayProxyRequestEvent input) {
        log.debug("validating request {}", input);
        if (isValid(input)) {
            return Optional.empty();
        }

        final JsonApiErrorDocument document = new JsonApiErrorDocument(new IdentityIdOrAliasNotProvidedError());
        log.debug("request is not valid, returning error document {}", document);
        return Optional.of(document);
    }

    private static boolean isValid(final APIGatewayProxyRequestEvent input) {
        if (hasIdPathParameter(input)) {
            log.debug("id path parameter provided, request is valid");
            return true;
        }

        if (hasAliasTypeAndValueQueryStringParameters(input)) {
            log.debug("aliasType and aliasValue query string parameters provided, request is valid");
            return true;
        }

        return false;
    }

    private static boolean hasIdPathParameter(final APIGatewayProxyRequestEvent input) {
        final Map<String, String> pathParameters = input.getPathParameters();
        return pathParameters != null && pathParameters.containsKey("id");
    }

    private static boolean hasAliasTypeAndValueQueryStringParameters(final APIGatewayProxyRequestEvent input) {
        final Map<String, String> queryStringParameters = input.getQueryStringParameters();
        return queryStringParameters != null &&
                queryStringParameters.containsKey("aliasType") &&
                queryStringParameters.containsKey("aliasValue");
    }

    public static class IdentityIdOrAliasNotProvidedError extends JsonApiErrorItem {

        private static final int STATUS_CODE = 400;
        private static final String TITLE = "Bad Request";
        private static final String DETAIL = "Either IDV ID or aliasType and aliasValue must be provided";

        public IdentityIdOrAliasNotProvidedError() {
            super(TITLE, DETAIL, Collections.emptyMap(), STATUS_CODE);
        }

    }

}
