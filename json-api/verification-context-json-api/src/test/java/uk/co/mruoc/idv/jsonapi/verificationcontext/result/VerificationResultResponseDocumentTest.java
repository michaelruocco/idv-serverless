package uk.co.mruoc.idv.jsonapi.verificationcontext.result;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextJsonConverterFactory;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class VerificationResultResponseDocumentTest {

    private static final String JSON = loadContentFromClasspath("/verification-result-response-document.json");
    private static final VerificationResultResponseDocument DOCUMENT = buildDocument();

    private static final JsonConverter CONVERTER = new JsonApiVerificationContextJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocument() {
        final String json = CONVERTER.toJson(DOCUMENT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeDocument() {
        final VerificationResultResponseDocument document = CONVERTER.toObject(JSON, VerificationResultResponseDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void canBeCreatedUsingIdFromResults() {
        final VerificationMethodResults results = buildResults();

        final VerificationResultResponseDocument document = new VerificationResultResponseDocument(results);

        assertThat(document.getId()).isEqualTo(results.getId());
        assertThat(document.getContextId()).isEqualTo(results.getContextId());
        assertThat(document.getResults()).isEqualTo(results);
    }

    private static VerificationResultResponseDocument buildDocument() {
        final VerificationMethodResults results = buildResults();
        return new VerificationResultResponseDocument(results);
    }

    private static VerificationMethodResults buildResults() {
        final UUID contextId = UUID.fromString("b648e9a6-4a6b-416c-95fb-194d95c0bea8");
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName("PUSH_NOTIFICATION")
                .methodName("PUSH_NOTIFICATION")
                .verificationId(UUID.fromString("a7609223-8c80-4e31-9bf8-e75d63cb998f"))
                .timestamp(Instant.parse("2019-03-10T21:51:54.638Z"))
                .successful(true)
                .build();
        return VerificationMethodResults.builder()
                .id(UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23"))
                .contextId(contextId)
                .results(Collections.singleton(result))
                .build();

    }

}