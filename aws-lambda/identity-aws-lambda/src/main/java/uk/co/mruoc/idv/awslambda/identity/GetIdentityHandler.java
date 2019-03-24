package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.RequestValidator;
import uk.co.mruoc.idv.awslambda.identity.error.GetIdentityErrorHandlerDelegator;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.json.identity.IdentityObjectMapperSingleton;

@Slf4j
@Builder
@AllArgsConstructor
public class GetIdentityHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final IdentityService identityService;
    private final RequestValidator requestValidator;
    private final AliasExtractor aliasExtractor;
    private final IdentityConverter identityConverter;
    private final ExceptionConverter exceptionConverter;

    public GetIdentityHandler() {
        this(new UkGetIdentityHandlerConfig());
    }

    public GetIdentityHandler(final GetIdentityHandlerConfig config) {
        this(builder()
                .identityService(config.getIdentityService())
                .requestValidator(new GetIdentityRequestValidator())
                .aliasExtractor(new AliasExtractor())
                .identityConverter(new IdentityConverter(IdentityObjectMapperSingleton.get()))
                .exceptionConverter(buildExceptionConverter(IdentityObjectMapperSingleton.get())));
    }

    public GetIdentityHandler(final GetIdentityHandlerBuilder builder) {
        this.identityService = builder.identityService;
        this.requestValidator = builder.requestValidator;
        this.aliasExtractor = builder.aliasExtractor;
        this.identityConverter = builder.identityConverter;
        this.exceptionConverter = builder.exceptionConverter;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            return handle(input);
        } catch (final Exception e) {
            final APIGatewayProxyResponseEvent response = exceptionConverter.toResponse(e);
            log.info("returning response {}", response);
            return response;
        }
    }

    private APIGatewayProxyResponseEvent handle(final APIGatewayProxyRequestEvent requestEvent) {
        log.info("handling request {}", requestEvent);
        requestValidator.validate(requestEvent);
        return loadIdentity(requestEvent);
    }

    private APIGatewayProxyResponseEvent loadIdentity(final APIGatewayProxyRequestEvent requestEvent) {
        final Alias alias = aliasExtractor.extractAlias(requestEvent);
        final Identity identity = identityService.load(alias);
        final APIGatewayProxyResponseEvent response = identityConverter.toResponseEvent(identity);
        log.info("returning response {}", response);
        return response;
    }

    private static ExceptionConverter buildExceptionConverter(final ObjectMapper mapper) {
        return ExceptionConverter.builder()
                .mapper(mapper)
                .errorHandler(new GetIdentityErrorHandlerDelegator())
                .build();
    }

}
