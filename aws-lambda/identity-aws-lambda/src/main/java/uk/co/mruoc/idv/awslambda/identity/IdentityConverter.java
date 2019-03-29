package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.identity.IdentityJsonApiDocument;

@RequiredArgsConstructor
public class IdentityConverter {

    private final JsonConverter converter;

    public IdentityJsonApiDocument toJsonApiDocument(final Identity identity) {
        return new IdentityJsonApiDocument(identity);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final Identity identity) {
        final IdentityJsonApiDocument document = toJsonApiDocument(identity);
        return toResponseEvent(document);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final IdentityJsonApiDocument document) {
        return new APIGatewayProxyResponseEvent()
                .withBody(converter.toJson(document))
                .withStatusCode(200);
    }

}
