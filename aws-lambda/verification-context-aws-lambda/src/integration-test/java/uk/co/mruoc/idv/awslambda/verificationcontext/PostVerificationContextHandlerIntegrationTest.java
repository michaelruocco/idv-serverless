package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;
import uk.co.mruoc.idv.events.sns.fake.FakeEventPublisher;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextJsonConverterFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class PostVerificationContextHandlerIntegrationTest {

    private final CreateVerificationContextService contextService = buildVerificationContextService();
    private final PostVerificationContextHandler handler = new PostVerificationContextHandler(contextService);

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

    private static String loadExpectedBody(final VerificationContextResponseDocument document) {
        final String template = loadContentFromClasspath("/post-verification-context-response.json");
        return VerificationContextBodyTemplatePopulator.populate(template, document);
    }

    private static VerificationContextResponseDocument toDocument(final String body) {
        final JsonConverter converter = new JsonApiVerificationContextJsonConverterFactory().build();
        return converter.toObject(body, VerificationContextResponseDocument.class);
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
                .build();
    }

    private static IdentityService buildIdentityService() {
        return IdentityService.builder()
                .dao(new FakeIdentityDao())
                .idvIdGenerator(new IdvIdGenerator())
                .aliasLoaderService(new StubbedAliasLoaderService())
                .build();
    }

}
