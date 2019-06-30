package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.lockoutstate.service.LoadLockoutStateService;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.verificationattempts.FakeVerificationAttemptsDao;

import java.util.HashMap;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class GetLockoutStateHandlerIntegrationTest {

    private static final IdentityDao IDENTITY_DAO = new FakeIdentityDao();
    private static final VerificationAttemptsDao ATTEMPTS_DAO = new FakeVerificationAttemptsDao();

    private final LoadLockoutStateService lockoutStateService = buildLockoutStateService();
    private final GetLockoutStateHandler handler = new GetLockoutStateHandler(lockoutStateService);

    private final Alias alias = new TokenizedCreditCardNumberAlias("3489347343788005");
    @Before
    public void setUp() {
        IDENTITY_DAO.save(Identity.withAliases(new IdvIdAlias(), alias));
    }

    @Test
    public void shouldGetLockoutState() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channelId", "RSA");
        parameters.put("activityType", "ONLINE_PURCHASE");
        parameters.put("aliasType", alias.getTypeName());
        parameters.put("aliasValue", alias.getValue());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(parameters);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(200);
        final String expectedResponseBody = loadContentFromClasspath("/put-reset-lockout-state-response.json");
        assertThatJson(response.getBody()).isEqualTo(expectedResponseBody);
    }

    @Test
    public void shouldReturnErrorIfRequiredParametersAreNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channelId", "RSA");
        parameters.put("activityType", "ONLINE_PURCHASE");
        parameters.put("aliasType", alias.getTypeName());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(parameters);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(500);
        final String expectedResponseBody = loadContentFromClasspath("/internal-server-error-response.json");
        assertThatJson(response.getBody()).isEqualTo(expectedResponseBody);
    }

    private static LoadLockoutStateService buildLockoutStateService() {
        final LoadLockoutStateServiceFactory factory = new LoadLockoutStateServiceFactory(ATTEMPTS_DAO, IDENTITY_DAO);
        return factory.build();
    }

}
