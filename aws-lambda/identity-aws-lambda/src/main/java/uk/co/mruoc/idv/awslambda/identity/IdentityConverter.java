package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.jsonapi.identity.IdentityJsonApiDocument;

import java.io.UncheckedIOException;

@RequiredArgsConstructor
public class IdentityConverter {

    private final ObjectMapper mapper;

    public IdentityJsonApiDocument toJsonApiDocument(final Identity identity) {
        return new IdentityJsonApiDocument(identity);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final Identity identity) {
        final IdentityJsonApiDocument document = toJsonApiDocument(identity);
        return toResponseEvent(document);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final IdentityJsonApiDocument document) {
        try {
            return new APIGatewayProxyResponseEvent()
                    .withBody(mapper.writeValueAsString(document))
                    .withStatusCode(200);
        } catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

}
