package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.IdPathParameterExtractor;
import uk.co.mruoc.idv.awslambda.verificationcontext.GetVerificationContextHandler.InvalidVerificationContextIdException;
import uk.co.mruoc.idv.awslambda.verificationcontext.GetVerificationContextHandler.VerificationContextIdNotProvidedException;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class GetVerificationContextHandlerTest {

    private final IdPathParameterExtractor idExtractor = mock(IdPathParameterExtractor.class);
    private final GetVerificationContextService service = mock(GetVerificationContextService.class);
    private final VerificationContextResponseFactory responseFactory = mock(VerificationContextResponseFactory.class);
    private final ExceptionConverter exceptionConverter = mock(ExceptionConverter.class);

    private final Context context = mock(Context.class);

    private final GetVerificationContextHandler handler = GetVerificationContextHandler.builder()
            .idExtractor(idExtractor)
            .service(service)
            .responseFactory(responseFactory)
            .exceptionConverter(exceptionConverter)
            .build();

    @Test
    public void shouldReturnErrorIfContextIdNotProvided() {
        final Map<String, String> pathParameters = new HashMap<>();
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);
        given(idExtractor.extractId(pathParameters)).willReturn(Optional.empty());

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(exceptionConverter.toResponse(any(VerificationContextIdNotProvidedException.class))).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldReturnErrorIfContextIdIsNotValidUuid() {
        final Map<String, String> pathParameters = new HashMap<>();
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);
        given(idExtractor.extractId(pathParameters)).willReturn(Optional.of("abc"));

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(exceptionConverter.toResponse(any(InvalidVerificationContextIdException.class))).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldLoadVerificationContext() {
        final UUID contextId = UUID.randomUUID();
        final Map<String, String> pathParameters = new HashMap<>();
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);
        given(idExtractor.extractId(pathParameters)).willReturn(Optional.of(contextId.toString()));

        final VerificationContext context = mock(VerificationContext.class);
        given(service.load(contextId)).willReturn(context);

        final APIGatewayProxyResponseEvent expectedResponseEvent = new APIGatewayProxyResponseEvent();
        given(responseFactory.toResponseEvent(context)).willReturn(expectedResponseEvent);

        final APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(request, null);

        assertThat(responseEvent).isEqualTo(expectedResponseEvent);
    }

}
