package uk.co.mruoc.idv.awslambda.authorizer.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;
import uk.co.mruoc.idv.core.authorizer.service.TokenService;
import uk.co.mruoc.idv.json.JsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.authorizer.GenerateTokenJsonConverterFactory;

@Slf4j
@Builder
@AllArgsConstructor
public class PostTokensHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final JsonConverterFactory JSON_CONVERTER_FACTORY = new GenerateTokenJsonConverterFactory();

    private final GenerateTokenRequestExtractor requestExtractor;
    private final TokenService service;
    private final GenerateTokenRequestConverter requestConverter;
    private final GenerateTokenResponseFactory responseFactory;
    private final ExceptionConverter exceptionConverter;

    public PostTokensHandler(final PostTokensHandlerBuilder builder) {
        this.requestExtractor = builder.requestExtractor;
        this.service = builder.service;
        this.requestConverter = builder.requestConverter;
        this.responseFactory = builder.responseFactory;
        this.exceptionConverter = builder.exceptionConverter;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            return createToken(input);
        } catch (final Exception e) {
            final APIGatewayProxyResponseEvent response = exceptionConverter.toResponse(e);
            log.info("returning response {}", response);
            return response;
        }
    }

    private APIGatewayProxyResponseEvent createToken(final APIGatewayProxyRequestEvent requestEvent) {
        log.info("handling request {}", requestEvent);
        final GenerateTokenRequest request = requestExtractor.extractRequest(requestEvent);
        final TokenRequest tokenRequest = requestConverter.toTokenRequest(request);
        final TokenResponse tokenResponse = service.create(tokenRequest);
        final APIGatewayProxyResponseEvent responseEvent = responseFactory.toResponseEvent(tokenResponse);
        log.info("returning response {}", responseEvent);
        return responseEvent;
    }

}
