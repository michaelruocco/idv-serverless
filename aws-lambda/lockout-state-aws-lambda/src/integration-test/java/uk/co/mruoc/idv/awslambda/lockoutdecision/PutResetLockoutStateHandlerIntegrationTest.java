package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.lockoutstate.UpdateLockoutStateService;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.verificationattempts.FakeVerificationAttemptsDao;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class PutResetLockoutStateHandlerIntegrationTest {

    private static final IdentityDao IDENTITY_DAO = new FakeIdentityDao();
    private static final VerificationAttemptsDao ATTEMPTS_DAO = new FakeVerificationAttemptsDao();

    private final UpdateLockoutStateService lockoutStateService = buildLockoutStateService();
    private final PutResetLockoutStateHandler handler = new PutResetLockoutStateHandler(lockoutStateService);

    @Before
    public void setUp() {
        IDENTITY_DAO.save(Identity.withAliases(new IdvIdAlias(), new TokenizedCreditCardNumberAlias("3489347343788005")));
    }

    @Test
    public void shouldReturnErrorForInvalidRequest() {
        final String requestBody = loadContentFromClasspath("/put-reset-lockout-state-invalid-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(500);
        final String expectedResponseBody = loadContentFromClasspath("/internal-server-error-response.json");
        assertThatJson(response.getBody()).isEqualTo(expectedResponseBody);
    }

    @Test
    public void shouldResetLockoutState() {
        final String requestBody = loadContentFromClasspath("/put-reset-lockout-state-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(200);
        final String expectedResponseBody = loadContentFromClasspath("/put-reset-lockout-state-response.json");
        assertThatJson(response.getBody()).isEqualTo(expectedResponseBody);
    }

    private static UpdateLockoutStateService buildLockoutStateService() {
        final UpdateLockoutStateServiceFactory factory = new UpdateLockoutStateServiceFactory(ATTEMPTS_DAO, IDENTITY_DAO);
        return factory.build();
    }

}
