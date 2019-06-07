package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.awslambda.verificationcontext.result.PostVerificationResultHandler;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.core.identity.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.lockoutdecision.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.result.RegisterAttemptsService;
import uk.co.mruoc.idv.core.verificationcontext.service.result.SequenceExtractor;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationMethodResultConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDao;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.lockoutdecision.FakeVerificationAttemptsDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationResultsDao;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.lockoutdecision.LockoutDecisionJsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextJsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultResponseDocument;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class PostVerificationResultsHandlerIntegrationTest {

    private static final String CHANNEL_ID = "CHANNEL";
    private static final Alias PROVIDED_ALIAS = new DefaultAlias(new DefaultAliasType("TYPE"), "FORMAT", "VALUE");

    private final VerificationContextDao contextDao = new FakeVerificationContextDao();
    private final VerificationResultsDao resultsDao = new FakeVerificationResultsDao();
    private final VerificationAttemptsDao attemptsDao = new FakeVerificationAttemptsDao();
    private final IdentityDao identityDao = new FakeIdentityDao();
    private final GetVerificationContextService loadContextService = buildVerificationContextService(contextDao);
    private final IdentityService identityService = buildIdentityService(identityDao);
    private final LoadVerificationAttemptsService loadAttemptsService = buildLoadAttemptsService(attemptsDao, identityService);
    private final LockoutStateService lockoutStateService = buildLockoutStateService(attemptsDao, loadAttemptsService);
    private final RegisterAttemptsService registerAttemptsService = buildRegisterAttemptsService(lockoutStateService);
    private final VerificationResultService resultService = buildVerificationResultService(resultsDao, loadContextService, registerAttemptsService);

    private final PostVerificationResultHandler handler = new PostVerificationResultHandler(resultService);

    @Before
    public void setUp() {
        identityService.upsert(buildIdentityRequest());
        final Identity identity = identityService.load(PROVIDED_ALIAS);
        contextDao.save(buildContext(identity));
    }

    @Test
    public void shouldSaveVerificationResults() throws JSONException {
        final String requestBody = loadContentFromClasspath("/post-verification-success-result-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        final VerificationResultResponseDocument document = toDocument(response.getBody());
        final String expectedBody = loadExpectedBody(document);
        assertThat(response.getStatusCode()).isEqualTo(201);
        JSONAssert.assertEquals(expectedBody, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void shouldCreateEmptyVerificationAttemptsForSuccessfulAttempt() {
        final String requestBody = loadContentFromClasspath("/post-verification-success-result-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        final VerificationResultResponseDocument document = toDocument(response.getBody());
        final VerificationAttempts attempts = extractAttempts(document);
        assertThat(attempts).isEmpty();
    }

    @Test
    public void shouldSaveVerificationAttemptsForFailureAttempt() throws JSONException {
        final String requestBody = loadContentFromClasspath("/post-verification-failure-result-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        final VerificationResultResponseDocument document = toDocument(response.getBody());
        final VerificationAttempts attempts = extractAttempts(document);
        final String attemptsJson = toJson(attempts);
        final String expectedJson = loadExpectedBody(attempts);
        System.out.println(attemptsJson);
        System.out.println(attemptsJson);
        JSONAssert.assertEquals(expectedJson, attemptsJson, JSONCompareMode.NON_EXTENSIBLE);
    }

    private VerificationAttempts extractAttempts(final VerificationResultResponseDocument document) {
        final UUID contextId = document.getContextId();
        final VerificationContext context = contextDao.load(contextId).get();
        final UUID idvId = context.getIdvId();
        return attemptsDao.loadByIdvId(idvId).get();
    }

    private static String toJson(final VerificationAttempts attempts) {
        final JsonConverter converter = new LockoutDecisionJsonConverterFactory().build();
        return converter.toJson(attempts);
    }

    private static String loadExpectedBody(final VerificationResultResponseDocument document) {
        final String template = loadContentFromClasspath("/post-verification-success-result-response.json");
        return VerificationResultBodyTemplatePopulator.populate(template, document);
    }

    private static VerificationResultResponseDocument toDocument(final String body) {
        final JsonConverter converter = new JsonApiVerificationContextJsonConverterFactory().build();
        return converter.toObject(body, VerificationResultResponseDocument.class);
    }

    private static String loadExpectedBody(final VerificationAttempts attempts) {
        final String templateJson = loadContentFromClasspath("/failed-attempt.json");
        return StringUtils.replaceEachRepeatedly(templateJson,
                new String[]{ "%IDV_ID%", "%LOCKOUT_STATE_ID%" },
                new String[]{ attempts.getIdvId().toString(), attempts.getLockoutStateId().toString() }
        );
    }

    private static VerificationResultService buildVerificationResultService(final VerificationResultsDao dao,
                                                                            final GetVerificationContextService loadContextService,
                                                                            final RegisterAttemptsService registerAttemptsService) {
        return VerificationResultService.builder()
                .uuidGenerator(new RandomUuidGenerator())
                .getContextService(loadContextService)
                .sequenceExtractor(new SequenceExtractor())
                .registerAttemptsService(registerAttemptsService)
                .dao(dao)
                .build();
    }

    private static GetVerificationContextService buildVerificationContextService(final VerificationContextDao dao) {
        return GetVerificationContextService.builder()
                .dao(dao)
                .timeService(new DefaultTimeService())
                .build();
    }

    private static VerificationContext buildContext(final Identity identity) {
        final Instant now = Instant.now();
        final VerificationMethod method = new PushNotificationVerificationMethod(VerificationMethod.DEFAULT_DURATION);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);
        return VerificationContext.builder()
                .id(UUID.fromString("b648e9a6-4a6b-416c-95fb-194d95c0bea8"))
                .channel(new DefaultChannel(CHANNEL_ID))
                .providedAlias(PROVIDED_ALIAS)
                .identity(identity)
                .sequences(Collections.singleton(sequence))
                .activity(new DefaultActivity("ACTIVITY", Instant.parse("2019-03-24T22:41:50.434Z")))
                .created(now)
                .expiry(now.plus(Duration.ofMinutes(5)))
                .build();
    }

    private UpsertIdentityRequest buildIdentityRequest() {
        return UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .providedAlias(PROVIDED_ALIAS)
                .build();
    }

    private static LockoutStateService buildLockoutStateService(final VerificationAttemptsDao dao,
                                                                final LoadVerificationAttemptsService loadAttemptsService) {
        return LockoutStateService.builder()
                .converter(new VerificationAttemptsConverter(new DefaultTimeService()))
                .policiesService(new StubbedLockoutPoliciesService())
                .loadAttemptsService(loadAttemptsService)
                .dao(dao)
                .build();
    }

    private static RegisterAttemptsService buildRegisterAttemptsService(final LockoutStateService lockoutStateService) {
        return RegisterAttemptsService.builder()
                .sequenceExtractor(new SequenceExtractor())
                .converter(new VerificationMethodResultConverter())
                .lockoutStateService(lockoutStateService)
                .build();
    }

    private static IdentityService buildIdentityService(final IdentityDao dao) {
        return IdentityService.builder()
                .idvIdGenerator(new IdvIdGenerator())
                .aliasLoaderService(new StubbedAliasLoaderService())
                .dao(dao)
                .build();
    }

    private static LoadVerificationAttemptsService buildLoadAttemptsService(final VerificationAttemptsDao dao,
                                                                            final IdentityService identityService) {
        return LoadVerificationAttemptsService.builder()
                .dao(dao)
                .identityService(identityService)
                .uuidGenerator(new RandomUuidGenerator())
                .build();
    }

}
