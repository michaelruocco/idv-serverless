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
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetIdentityHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ObjectMapper mapper;
    private final IdentityService identityService;
    private final RequestValidator requestValidator;

    public GetIdentityHandler() {
        this(ObjectMapperSingleton.get(), IdentityServiceSingleton.get(), new GetIdentityRequestValidator());
    }

    public GetIdentityHandler(final ObjectMapper mapper, final IdentityService identityService, final RequestValidator requestValidator) {
        this.mapper = mapper;
        this.identityService = identityService;
        this.requestValidator = requestValidator;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        final Optional<JsonApiErrorDocument> errorDocument = requestValidator.validate(input);
        if (errorDocument.isPresent()) {
            return toResponse(errorDocument.get());
        }
        return loadIdentity(input);
    }

    private APIGatewayProxyResponseEvent loadIdentity(final APIGatewayProxyRequestEvent input) {
        final Alias alias = extractAlias(input);
        final Identity identity = identityService.load(alias);
        final IdentityJsonApiDocument document = new IdentityJsonApiDocument(identity);
        return toResponse(document);
    }

    private static Alias extractAlias(final APIGatewayProxyRequestEvent input) {
        final Optional<String> id = extractId(input.getPathParameters());
        return id.map(s -> (Alias) new IdvIdAlias(UUID.fromString(s)))
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

    private APIGatewayProxyResponseEvent toResponse(final IdentityJsonApiDocument document) {
        try {
            return new APIGatewayProxyResponseEvent()
                    .withBody(mapper.writeValueAsString(document))
                    .withStatusCode(200);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private APIGatewayProxyResponseEvent toResponse(final JsonApiErrorDocument document) {
        try {
            return new APIGatewayProxyResponseEvent()
                    .withBody(mapper.writeValueAsString(document))
                    .withStatusCode(calculateErrorStatusCode(document));
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static int calculateErrorStatusCode(final JsonApiErrorDocument document) {
        final Set<Integer> codes = document.getErrors().stream()
                .map(JsonApiErrorItem::getStatusCode)
                .collect(Collectors.toSet());
        return codes.iterator().next();
    }

}
