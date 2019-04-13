package uk.co.mruoc.idv.awslambda.authorizer.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.authorizer.GenerateTokenRequestDocument;

@RequiredArgsConstructor
public class GenerateTokenRequestExtractor {

    private final JsonConverter converter;

    public GenerateTokenRequest extractRequest(final APIGatewayProxyRequestEvent input) {
        try {
            final GenerateTokenRequestDocument document = converter.toObject(input.getBody(), GenerateTokenRequestDocument.class);
            return document.getRequest();
        } catch (final JsonConversionException e) {
            throw new InvalidGenerateTokenRequestException(e);
        }
    }

    public static class InvalidGenerateTokenRequestException extends RuntimeException {

        public InvalidGenerateTokenRequestException(final Throwable cause) {
            super(cause);
        }

    }
}
