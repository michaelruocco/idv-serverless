package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.awslambda.verificationcontext.PostVerificationContextHandler;
import uk.co.mruoc.idv.awslambda.verificationcontext.CreateVerificationContextServiceFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.events.sns.fake.FakeEventPublisher;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextJsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class UkPostVerificationContextHandlerIntegrationTest {

    private final IdentityDao identityDao = new FakeIdentityDao();
    private final VerificationContextDao contextDao = new FakeVerificationContextDao();
    private final EventPublisher eventPublisher = new FakeEventPublisher();
    private final IdentityServiceFactory serviceFactory = new UkIdentityServiceFactory(identityDao);
    private final CreateVerificationContextServiceFactory factory = new UkPostVerificationContextServiceFactory(serviceFactory, contextDao, eventPublisher);
    private final PostVerificationContextHandler handler = new UkPostVerificationContextHandler(factory.build());

    @Test
    public void shouldCreateVerificationContext() throws JSONException {
        final String requestBody = loadContentFromClasspath("/post-verification-context-request.json");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        final VerificationContextResponseDocument document = toDocument(response.getBody());
        final String expectedBody = loadExpectedBody(document);
        assertThat(response.getStatusCode()).isEqualTo(201);
        JSONAssert.assertEquals(expectedBody, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    private String loadExpectedBody(final VerificationContextResponseDocument document) {
        final String template = loadContentFromClasspath("/post-verification-context-response.json");
        return VerificationContextBodyTemplatePopulator.populate(template, document);
    }

    private VerificationContextResponseDocument toDocument(final String body) {
        final JsonConverter converter = new JsonApiVerificationContextJsonConverterFactory().build();
        return converter.toObject(body, VerificationContextResponseDocument.class);
    }

}
