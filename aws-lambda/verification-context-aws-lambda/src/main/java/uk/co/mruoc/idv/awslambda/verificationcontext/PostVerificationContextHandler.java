package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequest;

@Slf4j
@Builder
public class PostVerificationContextHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final VerificationContextRequestExtractor requestExtractor;
    private final Facade facade;
    private final VerificationContextConverter contextConverter;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        return createContext(input);
    }

    private APIGatewayProxyResponseEvent createContext(final APIGatewayProxyRequestEvent requestEvent) {
        log.info("handling request {}", requestEvent);
        final VerificationContextRequest request = requestExtractor.extractRequest(requestEvent);
        final VerificationContext context = facade.create(request);
        final APIGatewayProxyResponseEvent responseEvent = contextConverter.toResponseEvent(context);
        log.info("returning response {}", responseEvent);
        return responseEvent;
    }

}
