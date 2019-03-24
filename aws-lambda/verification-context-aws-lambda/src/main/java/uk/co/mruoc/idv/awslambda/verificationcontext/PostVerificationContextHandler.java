package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.verificationcontext.error.PostVerificationContextErrorHandlerDelegator;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextObjectMapperSingleton;

@Slf4j
@Builder
@AllArgsConstructor
public class PostVerificationContextHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final VerificationContextRequestExtractor requestExtractor;
    private final CreateVerificationContextService service;
    private final VerificationContextResponseFactory responseFactory;
    private final ExceptionConverter exceptionConverter;

    public PostVerificationContextHandler(final CreateVerificationContextService service) {
        this(builder()
                .requestExtractor(new VerificationContextRequestExtractor(getObjectMapper()))
                .service(service)
                .responseFactory(new VerificationContextCreatedResponseFactory(getObjectMapper()))
                .exceptionConverter(buildExceptionConverter()));
    }

    public PostVerificationContextHandler(final PostVerificationContextHandlerBuilder builder) {
        this.requestExtractor = builder.requestExtractor;
        this.service = builder.service;
        this.responseFactory = builder.responseFactory;
        this.exceptionConverter = builder.exceptionConverter;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            return createContext(input);
        } catch (final Exception e) {
            final APIGatewayProxyResponseEvent response = exceptionConverter.toResponse(e);
            log.info("returning response {}", response);
            return response;
        }
    }

    private APIGatewayProxyResponseEvent createContext(final APIGatewayProxyRequestEvent requestEvent) {
        log.info("handling request {}", requestEvent);
        final VerificationContextRequest request = requestExtractor.extractRequest(requestEvent);
        final VerificationContext context = service.create(request);
        final APIGatewayProxyResponseEvent responseEvent = responseFactory.toResponseEvent(context);
        log.info("returning response {}", responseEvent);
        return responseEvent;
    }

    private static ExceptionConverter buildExceptionConverter() {
        return ExceptionConverter.builder()
                .mapper(getObjectMapper())
                .errorHandler(new PostVerificationContextErrorHandlerDelegator())
                .build();
    }

    private static ObjectMapper getObjectMapper() {
        return JsonApiVerificationContextObjectMapperSingleton.get();
    }

}
