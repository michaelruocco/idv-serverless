package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.identity.IdentityDaoFactory;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceSingleton;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextObjectMapperSingleton;

@Slf4j
public class PostVerificationContextHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final VerificationContextRequestExtractor requestExtractor;
    private final VerificationContextService service;
    private final VerificationContextConverter contextConverter;

    public PostVerificationContextHandler() {
        this(IdentityDaoFactory.build(Environment.getRegion(), Environment.getStage()),
                VerificationContextDaoFactory.build(Environment.getRegion(), Environment.getStage()));
    }

    public PostVerificationContextHandler(final IdentityDao identityDao, final VerificationContextDao verificationContextDao) {
        this(new VerificationContextRequestExtractor(JsonApiVerificationContextObjectMapperSingleton.get()),
                VerificationContextServiceSingleton.get(IdentityServiceSingleton.get(identityDao), verificationContextDao),
                new VerificationContextConverter(JsonApiVerificationContextObjectMapperSingleton.get()));
    }

    public PostVerificationContextHandler(final VerificationContextRequestExtractor requestExtractor,
                              final VerificationContextService service,
                              final VerificationContextConverter contextConverter) {
        this.requestExtractor = requestExtractor;
        this.service = service;
        this.contextConverter = contextConverter;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        return createContext(input);
    }

    private APIGatewayProxyResponseEvent createContext(final APIGatewayProxyRequestEvent requestEvent) {
        log.info("handling request {}", requestEvent);
        final VerificationContextRequest request = requestExtractor.extractRequest(requestEvent);
        final VerificationContext context = service.create(request);
        final APIGatewayProxyResponseEvent responseEvent = contextConverter.toResponseEvent(context);
        log.info("returning response {}", responseEvent);
        return responseEvent;
    }

}
