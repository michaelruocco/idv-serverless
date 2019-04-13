package uk.co.mruoc.idv.awslambda.authorizer.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.authorizer.model.AuthPolicyResponse;
import uk.co.mruoc.idv.core.authorizer.model.DecodedToken;
import uk.co.mruoc.idv.core.authorizer.model.PolicyRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenAuthorizerRequest;
import uk.co.mruoc.idv.core.authorizer.service.ApiGatewayMethodArnParser;
import uk.co.mruoc.idv.core.authorizer.service.PolicyLoader;
import uk.co.mruoc.idv.core.authorizer.service.TokenService;
import uk.co.mruoc.idv.json.authorizer.AuthPolicyConverter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizerHandler implements RequestHandler<TokenAuthorizerRequest, AuthPolicyResponse> {

    private final TokenService tokenService;
    private final PolicyLoader policyLoader;
    private final ApiGatewayMethodArnParser arnParser;
    private final AuthPolicyConverter policyConverter;

    @Override
    public AuthPolicyResponse handleRequest(final TokenAuthorizerRequest input, final Context context) {
        log.info("authorizer received input {}", input);
        final String token = input.getAuthorizationToken();
        final PolicyRequest policyRequest = PolicyRequest.builder()
                .principalId(extractPrincipalId(token))
                .methodArn(arnParser.parse(input.getMethodArn()))
                .build();
        return loadPolicy(policyRequest);
    }

    private String extractPrincipalId(final String token) {
        final DecodedToken decodedToken = tokenService.decode(token);
        final String principalId = decodedToken.getSubject();
        log.info("extracted principal id {} from token {}", principalId, token);
        return principalId;
    }

    private AuthPolicyResponse loadPolicy(final PolicyRequest request) {
        final String policyJson = policyLoader.load(request);
        return policyConverter.toAuthPolicyResponse(policyJson);
    }

}
