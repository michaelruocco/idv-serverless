package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.awslambda.verificationcontext.PostVerificationContextHandler;
import uk.co.mruoc.idv.awslambda.verificationcontext.CreateVerificationContextServiceFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutPoliciesService;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.lockoutdecision.FakeVerificationAttemptsDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.events.sns.fake.FakeEventPublisher;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa.UkLockoutPoliciesService;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class UkPostVerificationContextHandlerIntegrationTest {

    private final IdentityDao identityDao = new FakeIdentityDao();
    private final VerificationContextDao contextDao = new FakeVerificationContextDao();
    private final EventPublisher eventPublisher = new FakeEventPublisher();
    private final IdentityServiceFactory serviceFactory = new UkIdentityServiceFactory(identityDao);
    private final VerificationAttemptsDao attemptsDao = new FakeVerificationAttemptsDao();
    private final LockoutPoliciesService lockoutPoliciesService = new UkLockoutPoliciesService();
    private final CreateVerificationContextServiceFactory factory = new UkPostVerificationContextServiceFactory(serviceFactory,contextDao, eventPublisher, attemptsDao, lockoutPoliciesService);
    private final PostVerificationContextHandler handler = new UkPostVerificationContextHandler(factory.build());

    @Test
    public void shouldCreateVerificationContext() {
        final String requestBody = loadContentFromClasspath("/post-verification-context-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(201);
        final String expectedBody = loadContentFromClasspath("/post-verification-context-response.json");
        assertThatJson(response.getBody()).when(IGNORING_ARRAY_ORDER).isEqualTo(expectedBody);
    }

}
