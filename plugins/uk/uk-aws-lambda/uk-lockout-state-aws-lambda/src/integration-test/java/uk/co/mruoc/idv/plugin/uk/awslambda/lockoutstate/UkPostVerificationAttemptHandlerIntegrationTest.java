package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutstate;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.awslambda.lockoutdecision.PostVerificationAttemptHandler;
import uk.co.mruoc.idv.awslambda.lockoutdecision.UpdateLockoutStateServiceFactory;
import uk.co.mruoc.idv.awslambda.verificationcontext.GetVerificationContextServiceFactory;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.verificationattempts.FakeVerificationAttemptsDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext.UkGetVerificationContextServiceFactory;
import uk.co.mruoc.idv.plugin.uk.channel.RsaChannel;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class UkPostVerificationAttemptHandlerIntegrationTest {

    private final VerificationContextDao contextDao = new FakeVerificationContextDao();
    private final IdentityDao identityDao = new FakeIdentityDao();
    private final VerificationAttemptsDao attemptDao = new FakeVerificationAttemptsDao();

    private final GetVerificationContextServiceFactory getContextServiceFactory = new UkGetVerificationContextServiceFactory(contextDao);
    private final IdentityServiceFactory identityServiceFactory = new UkIdentityServiceFactory(identityDao);
    private final UpdateLockoutStateServiceFactory updateLockoutStateServiceFactory = new UkUpdateLockoutStateServiceFactory(getContextServiceFactory.build(), identityServiceFactory.build(), attemptDao);

    private final PostVerificationAttemptHandler handler = new UkPostVerificationAttemptHandler(updateLockoutStateServiceFactory.build());

    @Before
    public void setUp() {
        final Alias providedAlias = new TokenizedCreditCardNumberAlias("3489347343788005");
        final Identity identity = Identity.withAliases(new IdvIdAlias(), providedAlias);
        identityDao.save(identity);
        contextDao.save(buildContext(providedAlias, identity));
    }

    @Test
    public void shouldPostVerificationAttempt() {
        final String requestBody = loadContentFromClasspath("/post-verification-attempts-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(200);
        final String expectedBody = loadContentFromClasspath("/post-verification-attempts-response.json");
        assertThatJson(response.getBody()).isEqualTo(expectedBody);
    }

    private static VerificationContext buildContext(final Alias providedAlias, final Identity identity) {
        final Instant now = Instant.now();
        final VerificationMethod method = new PushNotificationVerificationMethod(VerificationMethod.DEFAULT_DURATION);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);
        return VerificationContext.builder()
                .id(UUID.fromString("b648e9a6-4a6b-416c-95fb-194d95c0bea8"))
                .channel(new RsaChannel())
                .providedAlias(providedAlias)
                .identity(identity)
                .sequences(Collections.singleton(sequence))
                .activity(new DefaultActivity("ACTIVITY", Instant.parse("2019-03-24T22:41:50.434Z")))
                .created(now)
                .expiry(now.plus(Duration.ofMinutes(5)))
                .build();
    }

}
