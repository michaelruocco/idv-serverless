package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutstate;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.awslambda.lockoutdecision.LoadVerificationAttemptsServiceFactory;
import uk.co.mruoc.idv.awslambda.lockoutdecision.PutResetLockoutStateHandler;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.verificationattempts.FakeVerificationAttemptsDao;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class UkPutResetLockoutStateHandlerIntegrationTest {

    private final IdentityDao identityDao = new FakeIdentityDao();
    private final IdentityServiceFactory identityServiceFactory = new UkIdentityServiceFactory(identityDao);
    private final VerificationAttemptsDao attemptsDao = new FakeVerificationAttemptsDao();
    private final LoadVerificationAttemptsServiceFactory factory = new UkLoadVerificationAttemptsServiceFactory(identityServiceFactory.build(), attemptsDao);

    private final Alias providedAlias = new TokenizedCreditCardNumberAlias("3489347343788005");
    private final Identity identity = Identity.withAliases(new IdvIdAlias(), providedAlias);
    private final VerificationAttempt attemptWithDifferentAlias = VerificationAttempt.builder()
            .contextId(UUID.randomUUID())
            .successful(false)
            .timestamp(Instant.now())
            .methodName("METHOD_NAME2")
            .channelId(UkChannel.Ids.RSA)
            .activityType("ACTIVITY2")
            .alias(identity.getIdvIdAlias())
            .build();

    private final PutResetLockoutStateHandler handler = new UkPutResetLockoutStateHandler(factory);

    @Before
    public void setUp() {
        identityDao.save(identity);

        final VerificationAttempts attempts = buildAttempts();
        attemptsDao.save(attempts);
    }

    @Test
    public void shouldResetLockoutState() {
        final String requestBody = loadContentFromClasspath("/put-reset-lockout-state-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(200);
        final String expectedBody = loadContentFromClasspath("/put-reset-lockout-state-response.json");
        assertThatJson(response.getBody()).isEqualTo(expectedBody);
        final Optional<VerificationAttempts> remainingAttempts = attemptsDao.loadByIdvId(identity.getIdvId());
        assertThat(remainingAttempts.get()).containsExactly(attemptWithDifferentAlias);
    }

    private VerificationAttempts buildAttempts() {
        final VerificationAttempt attemptWithMatchingAlias = VerificationAttempt.builder()
                .contextId(UUID.randomUUID())
                .successful(false)
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
