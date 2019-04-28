package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.verificationcontext.result.error.PostVerificationResultsErrorHandlerDelegator;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService;
import uk.co.mruoc.idv.json.JsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextJsonConverterFactory;

@Slf4j
@Builder
@AllArgsConstructor
public class PostVerificationResultHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final JsonConverterFactory JSON_CONVERTER_FACTORY = new JsonApiVerificationContextJsonConverterFactory();

    private final VerificationMethodResultsExtractor resultsExtractor;
    private final VerificationResultService service;
    private final VerificationMethodResultsResponseFactory responseFactory;
    private final ExceptionConverter exceptionConverter;
    private final StatusCalculator statusCalculator;

    public PostVerificationResultHandler(final VerificationResultService service) {
        this(builder()
                .resultsExtractor(new VerificationMethodResultsExtractor(JSON_CONVERTER_FACTORY.build()))
                .service(service)
                .responseFactory(new VerificationMethodResultsResponseFactory(JSON_CONVERTER_FACTORY.build()))
                .exceptionConverter(buildExceptionConverter())
                .statusCalculator(new StatusCalculator()));
    }

    public PostVerificationResultHandler(final PostVerificationResultHandlerBuilder builder) {
        this.resultsExtractor = builder.resultsExtractor;
        this.service = builder.service;
        this.responseFactory = builder.responseFactory;
        this.exceptionConverter = builder.exceptionConverter;
        this.statusCalculator = builder.statusCalculator;
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
        final VerificationMethodResults results = resultsExtractor.extractRequest(requestEvent);
        final VerificationMethodResults updatedResults = service.upsert(results);
        final int statusCode = statusCalculator.calculate(results, updatedResults);
        final APIGatewayProxyResponseEvent responseEvent = responseFactory.toResponseEvent(statusCode, updatedResults);
        log.info("returning response {}", responseEvent);
        return responseEvent;
    }

    private static ExceptionConverter buildExceptionConverter() {
        return ExceptionConverter.builder()
                .jsonConverter(JSON_CONVERTER_FACTORY.build())
                .errorHandler(new PostVerificationResultsErrorHandlerDelegator())
                .build();
    }

}
