package uk.co.mruoc.idv.awslambda.authorizer.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.authorizer.handler.GenerateTokenRequestExtractor.InvalidGenerateTokenRequestException;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;
import uk.co.mruoc.idv.core.authorizer.service.TokenService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class PostTokensHandlerTest {

    private final GenerateTokenRequestExtractor requestExtractor = mock(GenerateTokenRequestExtractor.class);
    private final TokenService service = mock(TokenService.class);
    private final GenerateTokenRequestConverter requestConverter = mock(GenerateTokenRequestConverter.class);
    private final GenerateTokenResponseFactory responseFactory = mock(GenerateTokenResponseFactory.class);
    private final ExceptionConverter exceptionConverter = mock(ExceptionConverter.class);

    private final Context lambdaContext = mock(Context.class);

    private final PostTokensHandler handler = PostTokensHandler.builder()
            .requestExtractor(requestExtractor)
            .service(service)
            .requestConverter(requestConverter)
            .responseFactory(responseFactory)
            .exceptionConverter(exceptionConverter)
            .build();

    @Test
    public void shouldReturnErrorIfExceptionIsThrown() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        final Exception exception = new InvalidGenerateTokenRequestException(new Exception());
        doThrow(exception).when(requestExtractor).extractRequest(request);

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(exceptionConverter.toResponse(exception)).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, lambdaContext);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldCreateToken() {
        final APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        final GenerateTokenRequest generateTokenRequest = mock(GenerateTokenRequest.class);
        given(requestExtractor.extractRequest(requestEvent)).willReturn(generateTokenRequest);

        final TokenRequest tokenRequest = mock(TokenRequest.class);
        given(requestConverter.toTokenRequest(generateTokenRequest)).willReturn(tokenRequest);
        final TokenResponse tokenResponse = mock(TokenResponse.class);
        given(service.create(tokenRequest)).willReturn(tokenResponse);

        final APIGatewayProxyResponseEvent expectedResponseEvent = new APIGatewayProxyResponseEvent();
        given(responseFactory.toResponseEvent(tokenResponse)).willReturn(expectedResponseEvent);

        final APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(requestEvent, lambdaContext);

        assertThat(responseEvent).isEqualTo(expectedResponseEvent);
    }

}
