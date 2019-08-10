package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.verificationattempts.model.RegisterAttemptsRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.lockoutstate.RegisterAttemptsRequestDocument;

@RequiredArgsConstructor
public class RegisterAttemptsRequestExtractor {

    private final JsonConverter converter;

    public final RegisterAttemptsRequest extractRequest(final APIGatewayProxyRequestEvent input) {
        try {
            final RegisterAttemptsRequestDocument document = converter.toObject(input.getBody(), RegisterAttemptsRequestDocument.class);
            return document.getRequest();
        } catch (final JsonConversionException e) {
            throw new InvalidRegisterAttemptsRequestException(e);
        }
    }

    public static class InvalidRegisterAttemptsRequestException extends RuntimeException {

        public InvalidRegisterAttemptsRequestException(final Throwable cause) {
            super(cause);
        }

        public InvalidRegisterAttemptsRequestException(final String message) {
            super(message);
        }

    }

}
