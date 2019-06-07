package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.LockoutStateRequestDocument;

@RequiredArgsConstructor
public class LockoutStateRequestExtractor {

    private final JsonConverter converter;

    public LockoutStateRequest extractRequest(final APIGatewayProxyRequestEvent input) {
        try {
            final LockoutStateRequestDocument document = converter.toObject(input.getBody(), LockoutStateRequestDocument.class);
            return document.getRequest();
        } catch (final JsonConversionException e) {
            throw new InvalidLockoutStateRequestException(e);
        }
    }

    public static class InvalidLockoutStateRequestException extends RuntimeException {

        public InvalidLockoutStateRequestException(final Throwable cause) {
            super(cause);
        }

    }

}
