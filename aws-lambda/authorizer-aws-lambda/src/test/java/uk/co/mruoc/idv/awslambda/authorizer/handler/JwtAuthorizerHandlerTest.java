package uk.co.mruoc.idv.awslambda.authorizer.handler;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.authorizer.model.ApiGatewayMethodArn;
import uk.co.mruoc.idv.core.authorizer.model.AuthPolicyResponse;
import uk.co.mruoc.idv.core.authorizer.model.DecodedToken;
import uk.co.mruoc.idv.core.authorizer.model.PolicyRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenAuthorizerRequest;
import uk.co.mruoc.idv.core.authorizer.service.ApiGatewayMethodArnParser;
import uk.co.mruoc.idv.core.authorizer.service.AuthPolicyConverter;
import uk.co.mruoc.idv.core.authorizer.service.PolicyLoader;
import uk.co.mruoc.idv.core.authorizer.service.TokenService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JwtAuthorizerHandlerTest {

    private final TokenService tokenService = mock(TokenService.class);
    private final PolicyLoader policyLoader = mock(PolicyLoader.class);
    private final ApiGatewayMethodArnParser arnParser = mock(ApiGatewayMethodArnParser.class);
    private final AuthPolicyConverter policyConverter = mock(AuthPolicyConverter.class);

    private final Context context = mock(Context.class);

    private final JwtAuthorizerHandler handler = new JwtAuthorizerHandler(tokenService, policyLoader, arnParser, policyConverter);

    @Test
    public void shouldPassPrincipalIdFromTokenAndParsedMethodArnToPolicyLoader() {
        final String token = "token";
        final DecodedToken decodedToken = mock(DecodedToken.class);
        given(tokenService.decode(token)).willReturn(decodedToken);

        final String principalId = "principalId";
        given(decodedToken.getSubject()).willReturn(principalId);

        final String rawMethodArn = "rawMethodArn";
        final ApiGatewayMethodArn methodArn = mock(ApiGatewayMethodArn.class);
        given(arnParser.parse(rawMethodArn)).willReturn(methodArn);

        handler.handleRequest(new TokenAuthorizerRequest("type", token, rawMethodArn), context);

        final ArgumentCaptor<PolicyRequest> captor = ArgumentCaptor.forClass(PolicyRequest.class);
        verify(policyLoader).load(captor.capture());
        final PolicyRequest request = captor.getValue();
        assertThat(request.getMethodArn()).isEqualTo(methodArn);
        assertThat(request.getPrincipalId()).isEqualTo(principalId);
    }

    @Test
    public void shouldReturnConvertedPolicyFromPolicyLoader() {
        final String token = "token";
        final DecodedToken decodedToken = mock(DecodedToken.class);
        given(tokenService.decode(token)).willReturn(decodedToken);

        final String principalId = "principalId";
        given(decodedToken.getSubject()).willReturn(principalId);

        final String rawMethodArn = "rawMethodArn";
        final ApiGatewayMethodArn methodArn = mock(ApiGatewayMethodArn.class);
        given(arnParser.parse(rawMethodArn)).willReturn(methodArn);

        final String policyJson = "policyJson";
        given(policyLoader.load(any(PolicyRequest.class))).willReturn(policyJson);

        final AuthPolicyResponse expectedResponse = mock(AuthPolicyResponse.class);
        given(policyConverter.toAuthPolicyResponse(policyJson)).willReturn(expectedResponse);

        final AuthPolicyResponse response = handler.handleRequest(new TokenAuthorizerRequest("type", token, rawMethodArn), context);

        assertThat(response).isEqualTo(expectedResponse);
    }

}
