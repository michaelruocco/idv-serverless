package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.AbstractVerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequestDocument;

@RequiredArgsConstructor
public class VerificationContextRequestExtractor {

    private final JsonConverter converter;

    public AbstractVerificationContextRequest extractRequest(final APIGatewayProxyRequestEvent input) {
        try {
            final VerificationContextRequestDocument document = converter.toObject(input.getBody(), VerificationContextRequestDocument.class);
            return document.getRequest();
        } catch (final JsonConversionException e) {
            throw new InvalidVerificationContextRequestException(e);
        }
    }

    public static class InvalidVerificationContextRequestException extends RuntimeException {

        public InvalidVerificationContextRequestException(final Throwable cause) {
            super(cause);
        }

    }

}
