package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.verificationcontext.VerificationContextRequestExtractor.InvalidVerificationContextRequestException;
import uk.co.mruoc.idv.core.verificationcontext.model.AbstractVerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class PostVerificationContextHandlerTest {

    private final VerificationContextRequestExtractor requestExtractor = mock(VerificationContextRequestExtractor.class);
    private final CreateVerificationContextService service = mock(CreateVerificationContextService.class);
    private final VerificationContextResponseFactory responseFactory = mock(VerificationContextResponseFactory.class);
    private final ExceptionConverter exceptionConverter = mock(ExceptionConverter.class);

    private final Context lambdaContext = mock(Context.class);

    private final PostVerificationContextHandler handler = PostVerificationContextHandler.builder()
            .requestExtractor(requestExtractor)
            .service(service)
            .responseFactory(responseFactory)
            .exceptionConverter(exceptionConverter)
            .build();

    @Test
    public void shouldReturnErrorIfExceptionIsThrown() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        final Exception exception = new InvalidVerificationContextRequestException(new Exception());
        doThrow(exception).when(requestExtractor).extractRequest(request);

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(exceptionConverter.toResponse(exception)).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, lambdaContext);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldCreateVerificationContext() {
        final APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        final AbstractVerificationContextRequest contextRequest = mock(AbstractVerificationContextRequest.class);
        given(requestExtractor.extractRequest(requestEvent)).willReturn(contextRequest);

        final VerificationContext context = mock(VerificationContext.class);
        given(service.create(contextRequest)).willReturn(context);

        final APIGatewayProxyResponseEvent expectedResponseEvent = new APIGatewayProxyResponseEvent();
        given(responseFactory.toResponseEvent(context)).willReturn(expectedResponseEvent);

        final APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(requestEvent, lambdaContext);

        assertThat(responseEvent).isEqualTo(expectedResponseEvent);
    }

}
