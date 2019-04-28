package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultResponseDocument;

@RequiredArgsConstructor
public class VerificationMethodResultsResponseFactory {

    private final JsonConverter jsonConverter;

    public VerificationResultResponseDocument toResponseDocument(final VerificationMethodResults results) {
        return new VerificationResultResponseDocument(results);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final int statusCode, final VerificationMethodResults results) {
        final VerificationResultResponseDocument document = toResponseDocument(results);
        return toResponseEvent(statusCode, document);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final int statusCode, final VerificationResultResponseDocument document) {
        return new APIGatewayProxyResponseEvent()
                .withBody(jsonConverter.toJson(document))
                .withStatusCode(statusCode);
    }

}
