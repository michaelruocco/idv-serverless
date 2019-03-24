package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityHandlerConfig;
import uk.co.mruoc.idv.awslambda.identity.UkGetIdentityHandlerConfig;
import uk.co.mruoc.idv.core.identity.service.IdentityDaoFactory;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDaoFactory;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDaoFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextObjectMapperSingleton;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class PostVerificationContextHandlerIntegrationTest {

    private final IdentityDaoFactory identityDaoFactory = new FakeIdentityDaoFactory();
    private final GetIdentityHandlerConfig identityConfig = new UkGetIdentityHandlerConfig(identityDaoFactory);
    private final VerificationContextDaoFactory contextDaoFactory = new FakeVerificationContextDaoFactory();
    private final PostVerificationContextHandlerConfig contextConfig = new UkVerificationContextHandlerConfig(identityConfig, contextDaoFactory);

    private final PostVerificationContextHandler handler = new PostVerificationContextHandler(contextConfig);

    @Test
    public void shouldCreateVerificationContext() throws IOException, JSONException {
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
        final String[] placeholders = new String[] {
                "%VERIFICATION_CONTEXT_ID%",
                "%IDV_ID%",
                "%CREATED%",
                "%EXPIRY%"
        };
        final String[] values = new String[] {
                document.getId().toString(),
                document.getIdvId().toString(),
                document.getCreated().toString(),
                document.getExpiry().toString()
        };
        final String template = loadContentFromClasspath("/post-verification-context-response.json");
        return StringUtils.replaceEachRepeatedly(template, placeholders, values);
    }

    private VerificationContextResponseDocument toDocument(final String body) throws IOException {
        final ObjectMapper mapper = JsonApiVerificationContextObjectMapperSingleton.get();
        return mapper.readValue(body, VerificationContextResponseDocument.class);
    }

}
