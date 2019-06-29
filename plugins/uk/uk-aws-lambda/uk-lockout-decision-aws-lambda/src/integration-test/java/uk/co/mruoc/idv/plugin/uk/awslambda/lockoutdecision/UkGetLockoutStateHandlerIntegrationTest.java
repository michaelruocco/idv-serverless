package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.awslambda.lockoutdecision.GetLockoutStateHandler;
import uk.co.mruoc.idv.awslambda.lockoutdecision.LoadVerificationAttemptsServiceFactory;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.lockoutdecision.FakeVerificationAttemptsDao;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class UkGetLockoutStateHandlerIntegrationTest {

    private final IdentityDao identityDao = new FakeIdentityDao();
    private final IdentityServiceFactory identityServiceFactory = new UkIdentityServiceFactory(identityDao);
    private final VerificationAttemptsDao attemptsDao = new FakeVerificationAttemptsDao();
    private final LoadVerificationAttemptsServiceFactory factory = new UkLoadVerificationAttemptsServiceFactory(identityServiceFactory.build(), attemptsDao);

    private final Alias providedAlias = new TokenizedCreditCardNumberAlias("3489347343788005");
    private final Identity identity = Identity.withAliases(new IdvIdAlias(), providedAlias);
    private final VerificationAttempt attemptWithDifferentAlias = VerificationAttempt.builder()
            .verificationId(UUID.fromString("0dcbf980-5a82-4b9f-9cfa-4f72959f392a"))
            .successful(false)
            .timestamp(Instant.now())
            .methodName("METHOD_NAME2")
            .channelId(UkChannel.Ids.RSA)
            .activityType("ONLINE_PURCHASE")
            .alias(identity.getIdvIdAlias())
            .build();

    private final GetLockoutStateHandler handler = new UkGetLockoutStateHandler(factory);

    @Before
    public void setUp() {
        identityDao.save(identity);

        final VerificationAttempts attempts = buildAttempts();
        attemptsDao.save(attempts);
    }

    @Test
    public void shouldGetLockoutState() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channelId", UkChannel.Ids.RSA);
        parameters.put("activityType", "ONLINE_PURCHASE");
        parameters.put("aliasType", providedAlias.getTypeName());
        parameters.put("aliasValue", providedAlias.getValue());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(parameters);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(200);
        final String expectedBody = loadContentFromClasspath("/get-lockout-state-response.json");
        assertThatJson(response.getBody()).isEqualTo(expectedBody);
    }

    private VerificationAttempts buildAttempts() {
        final VerificationAttempt attemptWithMatchingAlias = VerificationAttempt.builder()
                .successful(false)
                .verificationId(UUID.fromString("0dcbf980-5a82-4b9f-9cfa-4f72959f392a"))
                .timestamp(Instant.now())
                .methodName("METHOD_NAME1")
                .channelId(UkChannel.Ids.RSA)
                .activityType("ACTIVITY1")
                .alias(providedAlias)
                .build();
        return VerificationAttempts.builder()
                .lockoutStateId(UUID.fromString("1a72f67c-5a18-439e-92cd-a23cc24ab4e6"))
                .idvId(identity.getIdvId())
                .attempts(Arrays.asList(attemptWithMatchingAlias, attemptWithDifferentAlias))
                .build();
    }

}
