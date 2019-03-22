package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.identity.IdentityDaoFactory;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceSingleton;
import uk.co.mruoc.idv.awslambda.verificationcontext.VerificationContextRequestExtractor.InvalidVerificationContextRequestException;
import uk.co.mruoc.idv.awslambda.verificationcontext.error.PostVerificationContextErrorHandlerDelegator;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextObjectMapperSingleton;

@Slf4j
@Builder
@AllArgsConstructor
public class PostVerificationContextHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final VerificationContextRequestExtractor requestExtractor;
    private final VerificationContextService service;
    private final VerificationContextConverter contextConverter;
    private final ExceptionConverter exceptionConverter;

    public PostVerificationContextHandler() {
        this(IdentityDaoFactory.build(Environment.getRegion(), Environment.getStage()),
                VerificationContextDaoFactory.build(Environment.getRegion(), Environment.getStage()));
    }

    public PostVerificationContextHandler(final IdentityDao identityDao, final VerificationContextDao verificationContextDao) {
        this(builder()
                .requestExtractor(new VerificationContextRequestExtractor(JsonApiVerificationContextObjectMapperSingleton.get()))
                .service(VerificationContextServiceSingleton.get(IdentityServiceSingleton.get(identityDao), verificationContextDao))
                .contextConverter(new VerificationContextConverter(JsonApiVerificationContextObjectMapperSingleton.get()))
                .exceptionConverter(buildExceptionConverter()));
    }

    public PostVerificationContextHandler(final PostVerificationContextHandlerBuilder builder) {
        this.requestExtractor = builder.requestExtractor;
        this.service = builder.service;
        this.contextConverter = builder.contextConverter;
        this.exceptionConverter = builder.exceptionConverter;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            return createContext(input);
        } catch (final InvalidVerificationContextRequestException e) {
            final APIGatewayProxyResponseEvent response = exceptionConverter.toResponse(e);
            log.info("returning response {}", response);
            return response;
        }
    }

    private APIGatewayProxyResponseEvent createContext(final APIGatewayProxyRequestEvent requestEvent) {
        log.info("handling request {}", requestEvent);
        final VerificationContextRequest request = requestExtractor.extractRequest(requestEvent);
        final VerificationContext context = service.create(request);
        final APIGatewayProxyResponseEvent responseEvent = contextConverter.toResponseEvent(context);
        log.info("returning response {}", responseEvent);
        return responseEvent;
    }

    private static ExceptionConverter buildExceptionConverter() {
        final ObjectMapper mapper = JsonApiVerificationContextObjectMapperSingleton.get();
        return ExceptionConverter.builder()
                .mapper(mapper)
                .errorHandler(new PostVerificationContextErrorHandlerDelegator())
                .build();
    }

}
