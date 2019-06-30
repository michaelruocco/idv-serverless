package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.lockoutstate.DefaultLockoutStateResponse;
import uk.co.mruoc.idv.jsonapi.lockoutstate.LockoutStateResponse;
import uk.co.mruoc.idv.jsonapi.lockoutstate.LockoutStateResponseDocument;

@RequiredArgsConstructor
public class LockoutStateResponseFactory {

    private final int statusCode;
    private final JsonConverter jsonConverter;

    public LockoutStateResponseDocument toResponseDocument(final LockoutState state) {
        final LockoutStateResponse response = DefaultLockoutStateResponse.builder()
                .state(state)
                .build();
        return new LockoutStateResponseDocument(response);
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
