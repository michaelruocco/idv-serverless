package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultRegisterAttemptRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultRegisterAttemptsRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.RegisterAttemptRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.RegisterAttemptsRequest;
import uk.co.mruoc.idv.core.model.VerificationResult;
import uk.co.mruoc.idv.json.JsonConverter;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class RegisterAttemptsRequestDocumentTest {

    private static final String JSON = loadContentFromClasspath("/register-attempts-request-document.json");
    private static final RegisterAttemptsRequestDocument DOCUMENT = buildDocument();

    private static final JsonConverter CONVERTER = new JsonApiLockoutDecisionJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocument() {
        final String json = CONVERTER.toJson(DOCUMENT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeDocument() {
        final RegisterAttemptsRequestDocument document = CONVERTER.toObject(JSON, RegisterAttemptsRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void shouldReturnRequest() {
        final RegisterAttemptsRequest request = buildRequest();

        final RegisterAttemptsRequestDocument document = new RegisterAttemptsRequestDocument(request);

        assertThat(document.getRequest()).isEqualTo(request);
    }

    private static RegisterAttemptsRequestDocument buildDocument() {
        final RegisterAttemptsRequest request = buildRequest();
        return new RegisterAttemptsRequestDocument(request);
    }

    private static RegisterAttemptsRequest buildRequest() {
        final RegisterAttemptRequest attempt = DefaultRegisterAttemptRequest.builder()
                .methodName("METHOD_NAME")
                .verificationId(UUID.fromString("29c80561-6850-4d2f-ab56-69ea50768654"))
                .timestamp(Instant.parse("2019-03-10T12:53:57.547Z"))
                .result(VerificationResult.SUCCESS)
                .build();
        return DefaultRegisterAttemptsRequest.builder()
                .contextId(UUID.fromString("d98aa22c-a06e-4db5-8dc1-9ea83716ac12"))
                .attempts(Collections.singleton(attempt))
                .build();
    }

}