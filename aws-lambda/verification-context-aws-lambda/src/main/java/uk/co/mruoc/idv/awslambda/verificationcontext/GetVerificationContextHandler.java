package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.IdPathParameterExtractor;
import uk.co.mruoc.idv.awslambda.verificationcontext.error.GetVerificationContextErrorHandlerDelegator;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.LoadVerificationContextService;
import uk.co.mruoc.idv.json.JsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextJsonConverterFactory;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Builder
@AllArgsConstructor
public class GetVerificationContextHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final JsonConverterFactory JSON_CONVERTER_FACTORY = new JsonApiVerificationContextJsonConverterFactory();

    private final IdPathParameterExtractor idExtractor;
    private final LoadVerificationContextService service;
    private final VerificationContextResponseFactory responseFactory;
    private final ExceptionConverter exceptionConverter;

    public GetVerificationContextHandler(final LoadVerificationContextService service) {
        this(builder()
                .idExtractor(new IdPathParameterExtractor())
                .service(service)
                .responseFactory(new VerificationContextOkResponseFactory(JSON_CONVERTER_FACTORY.build()))
                .exceptionConverter(buildExceptionConverter()));
    }

    public GetVerificationContextHandler(final GetVerificationContextHandler.GetVerificationContextHandlerBuilder builder) {
        this.idExtractor = builder.idExtractor;
        this.service = builder.service;
        this.responseFactory = builder.responseFactory;
        this.exceptionConverter = builder.exceptionConverter;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            return loadContext(input);
        } catch (final Exception e) {
            final APIGatewayProxyResponseEvent response = exceptionConverter.toResponse(e);
            log.info("returning response {}", response);
            return response;
        }
    }

    private APIGatewayProxyResponseEvent loadContext(final APIGatewayProxyRequestEvent requestEvent) {
        log.info("handling request {}", requestEvent);
        final UUID id = extractId(requestEvent);
        final VerificationContext context = service.load(id);
        log.info("loaded context {}", context);
        final APIGatewayProxyResponseEvent responseEvent = responseFactory.toResponseEvent(context);
        log.info("returning response {}", responseEvent);
        return responseEvent;
    }

    private UUID extractId(final APIGatewayProxyRequestEvent requestEvent) {
        try {
            final Optional<String> id = idExtractor.extractId(requestEvent.getPathParameters());
            return id.map(UUID::fromString).orElseThrow(VerificationContextIdNotProvidedException::new);
        } catch (final IllegalArgumentException e) {
            throw new InvalidVerificationContextIdException(e);
        }
    }

    private static ExceptionConverter buildExceptionConverter() {
        return ExceptionConverter.builder()
                .jsonConverter(JSON_CONVERTER_FACTORY.build())
                .errorHandler(new GetVerificationContextErrorHandlerDelegator())
                .build();
    }

    public static class VerificationContextIdNotProvidedException extends RuntimeException {

        // intentionally blank

    }

    public static class InvalidVerificationContextIdException extends RuntimeException {

       public InvalidVerificationContextIdException(final Throwable cause) {
           super(cause);
       }

    }

}
