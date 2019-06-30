package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.lockoutdecision.LockoutStateRequestExtractor.InvalidLockoutStateRequestException;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.service.LoadLockoutStateService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class GetLockoutStateHandlerTest {

    private final GetLockoutStateRequestValidator requestValidator = mock(GetLockoutStateRequestValidator.class);
    private final GetLockoutStateRequestExtractor requestExtractor = mock(GetLockoutStateRequestExtractor.class);
    private final LoadLockoutStateService service = mock(LoadLockoutStateService.class);
    private final LockoutStateResponseFactory responseFactory = mock(LockoutStateResponseFactory.class);
    private final ExceptionConverter exceptionConverter = mock(ExceptionConverter.class);

    private final Context lambdaContext = mock(Context.class);

    private final GetLockoutStateHandler handler = GetLockoutStateHandler.builder()
            .requestValidator(requestValidator)
            .requestExtractor(requestExtractor)
            .service(service)
            .responseFactory(responseFactory)
            .exceptionConverter(exceptionConverter)
            .build();

    @Test
    public void shouldReturnErrorIfExceptionIsThrown() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        final Exception exception = new InvalidLockoutStateRequestException(new Exception());
        doThrow(exception).when(requestValidator).validate(request);

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(exceptionConverter.toResponse(exception)).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, lambdaContext);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldGetLockoutState() {
        final APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        final LockoutStateRequest resetRequest = mock(LockoutStateRequest.class);
        given(requestExtractor.extractRequest(requestEvent)).willReturn(resetRequest);

        final LockoutState state = mock(LockoutState.class);
        given(service.load(resetRequest)).willReturn(state);

        final APIGatewayProxyResponseEvent expectedResponseEvent = new APIGatewayProxyResponseEvent();
        given(responseFactory.toResponseEvent(state)).willReturn(expectedResponseEvent);

        final APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(requestEvent, lambdaContext);

        assertThat(responseEvent).isEqualTo(expectedResponseEvent);
    }

}
