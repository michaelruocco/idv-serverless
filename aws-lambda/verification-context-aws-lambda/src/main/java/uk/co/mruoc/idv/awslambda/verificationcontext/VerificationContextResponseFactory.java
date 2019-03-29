package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;

@RequiredArgsConstructor
public class VerificationContextResponseFactory {

    private final int statusCode;
    private final JsonConverter jsonConverter;

    public VerificationContextResponseDocument toResponseDocument(final VerificationContext context) {
        return new VerificationContextResponseDocument(context);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final VerificationContext context) {
        final VerificationContextResponseDocument document = toResponseDocument(context);
        return toResponseEvent(document);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final VerificationContextResponseDocument document) {
        return new APIGatewayProxyResponseEvent()
                .withBody(jsonConverter.toJson(document))
                .withStatusCode(statusCode);
    }

}
