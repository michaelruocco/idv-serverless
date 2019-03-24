package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.awslambda.verificationcontext.PostVerificationContextHandler;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextObjectMapperSingleton;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;
import uk.co.mruoc.idv.plugin.uk.identity.service.UkAliasLoaderService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility.UkEligibleMethodsService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.UkVerificationPoliciesService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class PostVerificationContextHandlerIntegrationTest {

    private final VerificationContextService contextService = buildVerificationContextService();
    private final PostVerificationContextHandler handler = new PostVerificationContextHandler(contextService);

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

    private VerificationContextService buildVerificationContextService() {
        return VerificationContextService.builder()
                .requestConverter(new VerificationContextRequestConverter())
                .identityService(buildIdentityService())
                .policiesService(new UkVerificationPoliciesService())
                .timeService(new DefaultTimeService())
                .eligibleMethodsService(new UkEligibleMethodsService())
                .expiryCalculator(new FixedExpiryCalculator())
                .idGenerator(new RandomUuidGenerator())
                .dao(new FakeVerificationContextDao())
                .build();
    }

    private IdentityService buildIdentityService() {
        return IdentityService.builder()
                .dao(new FakeIdentityDao())
                .idvIdGenerator(new IdvIdGenerator())
                .aliasLoaderService(new UkAliasLoaderService())
                .build();
    }

}
