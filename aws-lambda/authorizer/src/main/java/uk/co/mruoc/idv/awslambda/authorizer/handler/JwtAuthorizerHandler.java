package uk.co.mruoc.idv.awslambda.authorizer.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicyResponse;
import uk.co.mruoc.idv.awslambda.authorizer.model.PolicyRequest;
import uk.co.mruoc.idv.awslambda.authorizer.model.TokenAuthorizerRequest;
import uk.co.mruoc.idv.awslambda.authorizer.service.ApiGatewayMethodArnParser;
import uk.co.mruoc.idv.awslambda.authorizer.service.AuthPolicyConverter;
import uk.co.mruoc.idv.awslambda.authorizer.service.DecodedToken;
import uk.co.mruoc.idv.awslambda.authorizer.service.TokenService;
import uk.co.mruoc.idv.awslambda.authorizer.service.PolicyLoader;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizerHandler implements Function<TokenAuthorizerRequest, AuthPolicyResponse> {

    private final TokenService tokenService;
    private final PolicyLoader policyLoader;
    private final ApiGatewayMethodArnParser arnParser;
    private final AuthPolicyConverter policyConverter;

    @Override
    public AuthPolicyResponse apply(final TokenAuthorizerRequest input) {
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
