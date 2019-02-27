package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.jsonapi.identity.IdentityJsonApiDocument;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GetIdentityHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ObjectMapper mapper;
    private final IdentityService identityService;

    public GetIdentityHandler() {
        this(ObjectMapperSingleton.get(), IdentityServiceSingleton.get());
    }

    public GetIdentityHandler(final ObjectMapper mapper, final IdentityService identityService) {
        this.mapper = mapper;
        this.identityService = identityService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            final Alias alias = extractAlias(input);
            final Identity identity = identityService.load(alias);
            final IdentityJsonApiDocument document = new IdentityJsonApiDocument(identity);
            return new APIGatewayProxyResponseEvent()
                    .withBody(mapper.writeValueAsString(document))
                    .withStatusCode(200);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Alias extractAlias(final APIGatewayProxyRequestEvent input) {
        final Optional<String> id = extractId(input.getPathParameters());
        return id.map(s -> (Alias)new IdvIdAlias(UUID.fromString(s)))
                .orElseGet(() -> toAlias(input.getQueryStringParameters()));
    }

    private static Optional<String> extractId(Map<String, String> pathParameters) {
        if (pathParameters == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(pathParameters.get("id"));
    }

    private static Alias toAlias(final Map<String, String> queryStringParameters) {
        final String type = queryStringParameters.get("aliasType");
        final String format = queryStringParameters.getOrDefault("aliasFormat", Alias.Formats.CLEAR_TEXT);
        final String value = queryStringParameters.get("aliasValue");
        return new DefaultAlias(new DefaultAliasType(type), format, value);
    }

}
