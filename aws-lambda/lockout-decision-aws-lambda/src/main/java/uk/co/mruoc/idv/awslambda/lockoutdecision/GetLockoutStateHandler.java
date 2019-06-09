package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.AliasExtractor;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.lockoutdecision.error.PutResetLockoutStateErrorHandlerDelegator;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.json.JsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.JsonApiLockoutDecisionJsonConverterFactory;

@Slf4j
@Builder
@AllArgsConstructor
public class GetLockoutStateHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final JsonConverterFactory JSON_CONVERTER_FACTORY = new JsonApiLockoutDecisionJsonConverterFactory();

    private final LockoutStateRequestExtractor requestExtractor;
    private final LockoutStateService service;
    private final LockoutStateResponseFactory responseFactory;
    private final ExceptionConverter exceptionConverter;

    public GetLockoutStateHandler(final LockoutStateService service) {
        this(builder()
                .requestExtractor(new GetLockoutStateRequestExtractor(new AliasExtractor()))
                .service(service)
                .responseFactory(new LockoutStateOkResponseFactory(JSON_CONVERTER_FACTORY.build()))
                .exceptionConverter(buildExceptionConverter()));
    }

    public GetLockoutStateHandler(final GetLockoutStateHandlerBuilder builder) {
        this.requestExtractor = builder.requestExtractor;
        this.service = builder.service;
        this.responseFactory = builder.responseFactory;
        this.exceptionConverter = builder.exceptionConverter;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            return getLockoutState(input);
        } catch (final Exception e) {
            log.error("caught exception trying to create verification context", e);
            final APIGatewayProxyResponseEvent response = exceptionConverter.toResponse(e);
            log.info("returning response {}", response);
            return response;
        }
    }

    private APIGatewayProxyResponseEvent getLockoutState(final APIGatewayProxyRequestEvent requestEvent) {
        log.info("handling request {}", requestEvent);
        final LockoutStateRequest request = requestExtractor.extractRequest(requestEvent);
        final LockoutState lockoutState = service.load(request);
        final APIGatewayProxyResponseEvent responseEvent = responseFactory.toResponseEvent(lockoutState);
        log.info("returning response {}", responseEvent);
        return responseEvent;
    }

    private static ExceptionConverter buildExceptionConverter() {
        return ExceptionConverter.builder()
                .jsonConverter(JSON_CONVERTER_FACTORY.build())
                .errorHandler(new PutResetLockoutStateErrorHandlerDelegator())
                .build();
    }

}
