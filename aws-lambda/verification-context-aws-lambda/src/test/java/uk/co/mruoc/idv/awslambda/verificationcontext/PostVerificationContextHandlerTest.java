package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.verificationcontext.VerificationContextRequestExtractor.InvalidVerificationContextRequestException;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class PostVerificationContextHandlerTest {

    private final VerificationContextRequestExtractor requestExtractor = mock(VerificationContextRequestExtractor.class);
    private final VerificationContextService service = mock(VerificationContextService.class);
    private final VerificationContextConverter contextConverter = mock(VerificationContextConverter.class);
    private final ExceptionConverter exceptionConverter = mock(ExceptionConverter.class);

    private final Context context = mock(Context.class);

    private final PostVerificationContextHandler handler = PostVerificationContextHandler.builder()
            .requestExtractor(requestExtractor)
            .service(service)
            .contextConverter(contextConverter)
            .exceptionConverter(exceptionConverter)
            .build();

    @Test
    public void shouldReturnErrorIfRequestIsInvalid() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        final Exception exception = new InvalidVerificationContextRequestException(new Exception());
        doThrow(exception).when(requestExtractor).extractRequest(request);

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(exceptionConverter.toResponse(exception)).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldPassCreateVerificationContext() {
        final APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        final VerificationContextRequest contextRequest = mock(VerificationContextRequest.class);
        given(requestExtractor.extractRequest(requestEvent)).willReturn(contextRequest);

        final VerificationContext context = mock(VerificationContext.class);
        given(service.create(contextRequest)).willReturn(context);

        final APIGatewayProxyResponseEvent expectedResponseEvent = new APIGatewayProxyResponseEvent();
        given(contextConverter.toResponseEvent(context)).willReturn(expectedResponseEvent);

        final APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(requestEvent, null);

        assertThat(responseEvent).isEqualTo(expectedResponseEvent);
    }

}
