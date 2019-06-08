package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
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
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.lockoutdecision.FakeVerificationAttemptsDao;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.JsonApiLockoutDecisionJsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.LockoutStateResponseDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class PutResetLockoutStateHandlerIntegrationTest {

    private static final IdentityDao IDENTITY_DAO = new FakeIdentityDao();
    private static final VerificationAttemptsDao ATTEMPTS_DAO = new FakeVerificationAttemptsDao();

    private final LockoutStateService lockoutStateService = buildLockoutStateService();
    private final PutResetLockoutStateHandler handler = new PutResetLockoutStateHandler(lockoutStateService);

    @Before
    public void setUp() {
        IDENTITY_DAO.save(Identity.withAliases(new IdvIdAlias(), new TokenizedCreditCardNumberAlias("3489347343788005")));
    }

    @Test
    public void shouldResetLockoutState() throws JSONException {
        final String requestBody = loadContentFromClasspath("/put-reset-lockout-state-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        final LockoutStateResponseDocument document = toDocument(response.getBody());
        final String expectedBody = loadExpectedBody(document);
        assertThat(response.getStatusCode()).isEqualTo(201);
        JSONAssert.assertEquals(expectedBody, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    private static String loadExpectedBody(final LockoutStateResponseDocument document) {
        final String template = loadContentFromClasspath("/put-reset-lockout-state-response.json");
        return LockoutStateBodyTemplatePopulator.populate(template, document);
    }

    private static LockoutStateResponseDocument toDocument(final String body) {
        final JsonConverter converter = new JsonApiLockoutDecisionJsonConverterFactory().build();
        return converter.toObject(body, LockoutStateResponseDocument.class);
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

    private static IdentityService buildIdentityService() {
        return IdentityService.builder()
                .dao(IDENTITY_DAO)
                .idvIdGenerator(new IdvIdGenerator())
                .aliasLoaderService(new StubbedAliasLoaderService())
                .build();
    }

}
