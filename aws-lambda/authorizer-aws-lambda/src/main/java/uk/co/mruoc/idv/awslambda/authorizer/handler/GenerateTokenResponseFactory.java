package uk.co.mruoc.idv.awslambda.authorizer.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.authorizer.GenerateTokenResponseDocument;

import java.util.UUID;

@RequiredArgsConstructor
public class GenerateTokenResponseFactory {

    private static final int CREATED_STATUS_CODE = 201;

    private final JsonConverter jsonConverter;
    private final UuidGenerator uuidGenerator;

    public GenerateTokenResponseDocument toResponseDocument(final TokenResponse response) {
        final UUID id = uuidGenerator.randomUuid();
        return new GenerateTokenResponseDocument(id, response);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final TokenResponse response) {
        final GenerateTokenResponseDocument document = toResponseDocument(response);
        return toResponseEvent(document);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final GenerateTokenResponseDocument document) {
        return new APIGatewayProxyResponseEvent()
                .withBody(jsonConverter.toJson(document))
                .withStatusCode(CREATED_STATUS_CODE);
    }

}
