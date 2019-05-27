package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultRequestDocument;

@RequiredArgsConstructor
public class VerificationMethodResultsExtractor {

    private final JsonConverter converter;

    public VerificationMethodResults extractRequest(final APIGatewayProxyRequestEvent input) {
        try {
            final VerificationResultRequestDocument document = converter.toObject(input.getBody(), VerificationResultRequestDocument.class);
            final VerificationMethodResults results = document.getResults();
            if (results.isEmpty()) {
                throw new InvalidVerificationMethodResultsException("results array must not be empty");
            }
            return document.getResults();
        } catch (final JsonConversionException e) {
            throw new InvalidVerificationMethodResultsException(e);
        }
    }

    public static class InvalidVerificationMethodResultsException extends RuntimeException {

        public InvalidVerificationMethodResultsException(final Throwable cause) {
            super(cause);
        }

        public InvalidVerificationMethodResultsException(final String message) {
            super(message);
        }

    }

}
