package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.LockoutStateResponseDocument;

@RequiredArgsConstructor
public class LockoutStateResponseFactory {

    private final int statusCode;
    private final JsonConverter jsonConverter;

    public LockoutStateResponseDocument toResponseDocument(final LockoutState lockoutState) {
        return new LockoutStateResponseDocument(lockoutState);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final LockoutState lockoutState) {
        final LockoutStateResponseDocument document = toResponseDocument(lockoutState);
        return toResponseEvent(document);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final LockoutStateResponseDocument document) {
        return new APIGatewayProxyResponseEvent()
                .withBody(jsonConverter.toJson(document))
                .withStatusCode(statusCode);
    }

}
