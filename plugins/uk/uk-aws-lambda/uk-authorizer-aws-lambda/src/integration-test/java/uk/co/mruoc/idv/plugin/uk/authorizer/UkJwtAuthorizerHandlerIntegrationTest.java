package uk.co.mruoc.idv.plugin.uk.authorizer;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.file.ContentLoader;
import uk.co.mruoc.idv.awslambda.authorizer.handler.JwtAuthorizerHandler;
import uk.co.mruoc.idv.core.authorizer.model.AuthPolicyResponse;
import uk.co.mruoc.idv.core.authorizer.model.TokenAuthorizerRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;
import uk.co.mruoc.idv.core.authorizer.service.DefaultTokenRequest;
import uk.co.mruoc.idv.core.authorizer.service.TokenService;

import java.io.UncheckedIOException;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class UkJwtAuthorizerHandlerIntegrationTest {

    private static final String SECRET_KEY = "secretKey";

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    private final Context context = mock(Context.class);
    private final TokenService tokenService = new UkJwtTokenService(SECRET_KEY);

    private JwtAuthorizerHandler handler;

    @Before
    public void setUp() {
        environmentVariables.set("AUTHORIZER_SECRET_KEY", SECRET_KEY);
        handler = new UkJwtAuthorizerHandler();
    }

    @After
    public void tearDown() {
        environmentVariables.clear("AUTHORIZER_SECRET_KEY");
    }

    @Test
    public void shouldReturnAllowAllPolicyForTokenForIdvTestUserPrincipalId() throws JSONException {
        final String principalId = "idv-test-user";
        final TokenResponse tokenResponse = tokenService.create(DefaultTokenRequest.builder()
                .id(UUID.randomUUID())
                .subject(principalId)
                .build());
        final String token = tokenResponse.getToken();
        final String methodArn = "arn:aws:execute-api:eu-west-1:327122349051:8tu67utdf7/dev/GET/verificationContexts/*";

        final AuthPolicyResponse response = handler.handleRequest(new TokenAuthorizerRequest("type", token, methodArn), context);

        final String expectedJson = ContentLoader.loadContentFromClasspath("/allow-all-policy.json");
        JSONAssert.assertEquals(expectedJson, toJson(response), JSONCompareMode.STRICT);
    }

    @Test
    public void shouldReturnDenyAllPolicyForTokenForAnyOtherPrincipalId() throws JSONException {
        final String principalId = "other-user";
        final TokenResponse tokenResponse = tokenService.create(DefaultTokenRequest.builder()
                .id(UUID.randomUUID())
                .subject(principalId)
                .build());
        final String token = tokenResponse.getToken();
        final String methodArn = "arn:aws:execute-api:eu-west-1:327122349051:8tu67utdf7/dev/GET/verificationContexts/*";

        final AuthPolicyResponse response = handler.handleRequest(new TokenAuthorizerRequest("type", token, methodArn), context);

        final String expectedJson = ContentLoader.loadContentFromClasspath("/deny-all-policy.json");
        JSONAssert.assertEquals(expectedJson, toJson(response), JSONCompareMode.STRICT);
    }

    private static String toJson(final AuthPolicyResponse response) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(response);
        } catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

}
