package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.lockoutdecision.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.lockoutdecision.FakeVerificationAttemptsDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;
import uk.co.mruoc.idv.events.sns.fake.FakeEventPublisher;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class PostVerificationContextHandlerIntegrationTest {

    private static final IdentityDao IDENTITY_DAO = new FakeIdentityDao();
    private static final VerificationAttemptsDao ATTEMPTS_DAO = new FakeVerificationAttemptsDao();

    private final CreateVerificationContextService contextService = buildVerificationContextService();
    private final PostVerificationContextHandler handler = new PostVerificationContextHandler(contextService);

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

    private static CreateVerificationContextService buildVerificationContextService() {
        final TimeService timeService = new DefaultTimeService();
        return CreateVerificationContextService.builder()
                .requestConverter(new VerificationContextRequestConverter())
                .identityService(buildIdentityService())
                .policiesService(new StubbedVerificationPoliciesService())
                .timeService(timeService)
                .verificationMethodsService(new StubbedVerificationMethodsService())
                .expiryCalculator(new FixedExpiryCalculator())
                .idGenerator(new RandomUuidGenerator())
                .dao(new FakeVerificationContextDao())
                .contextConverter(new VerificationContextConverter(timeService))
                .eventPublisher(new FakeEventPublisher())
                .lockoutStateService(buildLockoutStateService())
                .build();
    }

    private static IdentityService buildIdentityService() {
        return IdentityService.builder()
                .dao(IDENTITY_DAO)
                .idvIdGenerator(new IdvIdGenerator())
                .aliasLoaderService(new StubbedAliasLoaderService())
                .build();
    }

    private static LockoutStateService buildLockoutStateService() {
        final TimeService timeService = new DefaultTimeService();
        final LoadVerificationAttemptsService attemptsService = buildLoadAttemptsService();
        return LockoutStateService.builder()
                .dao(new FakeVerificationAttemptsDao())
                .policiesService(new StubbedLockoutPoliciesService())
                .converter(new VerificationAttemptsConverter(timeService))
                .loadAttemptsService(attemptsService)
                .build();
    }

    private static LoadVerificationAttemptsService buildLoadAttemptsService() {
        final IdentityService identityService = buildIdentityService();
        return LoadVerificationAttemptsService.builder()
                .identityService(identityService)
                .uuidGenerator(new RandomUuidGenerator())
                .dao(ATTEMPTS_DAO)
                .build();
    }

}
