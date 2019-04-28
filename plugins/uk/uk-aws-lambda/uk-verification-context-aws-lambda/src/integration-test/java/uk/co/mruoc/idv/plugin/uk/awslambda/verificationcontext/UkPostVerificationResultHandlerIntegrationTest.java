package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.awslambda.verificationcontext.GetVerificationContextServiceFactory;
import uk.co.mruoc.idv.awslambda.verificationcontext.result.PostVerificationResultHandler;
import uk.co.mruoc.idv.awslambda.verificationcontext.result.VerificationResultsServiceFactory;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationResultsDao;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextJsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultResponseDocument;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class UkPostVerificationResultHandlerIntegrationTest {

    private final VerificationContextDao contextDao = new FakeVerificationContextDao();
    private final VerificationResultsDao resultDao = new FakeVerificationResultsDao();
    private final GetVerificationContextServiceFactory getContextFactory = new UkGetVerificationContextServiceFactory(contextDao);
    private final VerificationResultsServiceFactory postResultsFactory = new UkPostVerificationResultServiceFactory(getContextFactory.build(), resultDao);

    private final PostVerificationResultHandler handler = new UkPostVerificationResultHandler(postResultsFactory.build());

    @Before
    public void setUp() {
        System.out.println("saving to dao " + contextDao);
        contextDao.save(buildContext());
    }

    @Test
    public void shouldCreateVerificationContext() throws JSONException {
        final String requestBody = loadContentFromClasspath("/post-verification-result-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        System.out.println("response body " + response.getBody());
        final VerificationResultResponseDocument document = toDocument(response.getBody());
        final String expectedBody = loadExpectedBody(document);
        assertThat(response.getStatusCode()).isEqualTo(201);
        JSONAssert.assertEquals(expectedBody, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    private static String loadExpectedBody(final VerificationResultResponseDocument document) {
        final String template = loadContentFromClasspath("/post-verification-result-response.json");
        return VerificationResultBodyTemplatePopulator.populate(template, document);
    }

    private static VerificationResultResponseDocument toDocument(final String body) {
        final JsonConverter converter = new JsonApiVerificationContextJsonConverterFactory().build();
        return converter.toObject(body, VerificationResultResponseDocument.class);
    }

    private static VerificationContext buildContext() {
        final Instant now = Instant.now();
        final Alias providedAlias = new DefaultAlias(new DefaultAliasType("TYPE"), "FORMAT", "VALUE");
        final VerificationMethod method = new PushNotificationVerificationMethod(VerificationMethod.DEFAULT_DURATION);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);
        return VerificationContext.builder()
                .id(UUID.fromString("b648e9a6-4a6b-416c-95fb-194d95c0bea8"))
                .channel(new DefaultChannel("CHANNEL"))
                .providedAlias(providedAlias)
                .identity(Identity.withAliases(new IdvIdAlias(), providedAlias))
                .sequences(Collections.singleton(sequence))
                .activity(new DefaultActivity("ACTIVITY", Instant.parse("2019-03-24T22:41:50.434Z")))
                .created(now)
                .expiry(now.plus(Duration.ofMinutes(5)))
                .build();
    }

}
