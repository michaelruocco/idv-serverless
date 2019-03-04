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
        final Map<String, String> pathParameters = input.getPathParameters();
        if (pathParameters != null && pathParameters.containsKey("id")) {
            log.debug("id path parameter provided, request is valid");
            return Optional.empty();
        }

        final Map<String, String> queryStringParameters = input.getQueryStringParameters();
        if (queryStringParameters.containsKey("aliasType") && queryStringParameters.containsKey("aliasValue")) {
            log.debug("aliasType and aliasValue query string parameters provided, request is valid");
            return Optional.empty();
        }

        final JsonApiErrorDocument document = new JsonApiErrorDocument(new IdentityIdOrAliasNotProvidedError());
        log.debug("request is not valid, returning error document {}", document);
        return Optional.of(document);
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
