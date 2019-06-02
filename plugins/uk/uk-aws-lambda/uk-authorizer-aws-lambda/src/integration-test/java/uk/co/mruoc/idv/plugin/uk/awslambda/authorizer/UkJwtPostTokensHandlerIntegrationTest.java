package uk.co.mruoc.idv.plugin.uk.awslambda.authorizer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.assertj.core.api.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import uk.co.mruoc.idv.awslambda.authorizer.handler.PostTokensHandler;
import uk.co.mruoc.idv.core.authorizer.model.DecodedToken;
import uk.co.mruoc.idv.core.authorizer.service.TokenService;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.authorizer.GenerateTokenJsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.authorizer.GenerateTokenResponseDocument;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class UkJwtPostTokensHandlerIntegrationTest {

    private static final String SECRET_KEY = "secretKey";
    private static final Condition<String> VALID_UUID = new IsValidUUIDCondition();

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    private final Context context = mock(Context.class);
    private final TokenService tokenService = new UkJwtTokenService(SECRET_KEY);
    private final JsonConverter jsonConverter = new GenerateTokenJsonConverterFactory().build();

    private PostTokensHandler handler;

    @Before
    public void setUp() {
        environmentVariables.set("AUTHORIZER_SECRET_KEY", SECRET_KEY);
        handler = new UkJwtPostTokensHandler();
    }

    @After
    public void tearDown() {
        environmentVariables.clear("AUTHORIZER_SECRET_KEY");
    }

    @Test
    public void shouldGenerateTokenWithStandardFields() {
        final String requestBody = loadContentFromClasspath("/post-non-expiring-token-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        final GenerateTokenResponseDocument document = jsonConverter.toObject(response.getBody(), GenerateTokenResponseDocument.class);
        final DecodedToken decodedToken = tokenService.decode(document.getToken());
        assertThat(decodedToken.getSubject()).isEqualTo("idv-test-user");
        assertThat(decodedToken.getIssuer()).isEqualTo("uk-idv");
        assertThat(decodedToken.getId()).is(VALID_UUID);
        assertThat(decodedToken.getAudience()).isNull();
        assertThat(decodedToken.getIssuedAt()).isCloseTo(Instant.now(), within(2, ChronoUnit.SECONDS));
    }

    @Test
    public void shouldGenerateNonExpiringToken() {
        final String requestBody = loadContentFromClasspath("/post-non-expiring-token-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        final GenerateTokenResponseDocument document = jsonConverter.toObject(response.getBody(), GenerateTokenResponseDocument.class);
        final DecodedToken decodedToken = tokenService.decode(document.getToken());
        assertThat(decodedToken.getExpiration()).isEmpty();
    }

    @Test
    public void shouldGenerateExpiringToken() {
        final String requestBody = loadContentFromClasspath("/post-expiring-token-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        final GenerateTokenResponseDocument document = jsonConverter.toObject(response.getBody(), GenerateTokenResponseDocument.class);
        final DecodedToken decodedToken = tokenService.decode(document.getToken());
        assertThat(decodedToken.getExpiration()).isNotEmpty();
        final Instant expectedExpiry = decodedToken.getIssuedAt().plusSeconds(60);
        assertThat(decodedToken.getExpiration()).isEqualTo(Optional.of(expectedExpiry));
    }

}
