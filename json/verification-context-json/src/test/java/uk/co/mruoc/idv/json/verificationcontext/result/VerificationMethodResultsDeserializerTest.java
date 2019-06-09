package uk.co.mruoc.idv.json.verificationcontext.result;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationResult;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

@Slf4j
public class VerificationMethodResultsDeserializerTest {

    private static final String RESULTS_WITH_IDS_PATH = "/result/results-with-ids.json";
    private static final String RESULTS_WITHOUT_IDS_PATH = "/result/results-without-ids.json";

    private static final JsonConverter CONVERTER = new VerificationContextJsonConverterFactory().build();

    @Test
    public void shouldSerializeResults() {
        final VerificationMethodResults results = buildResults();

        final String json = CONVERTER.toJson(results);

        final String expectedJson = loadContentFromClasspath(RESULTS_WITH_IDS_PATH);
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeResultsWithIds() {
        final String json = loadContentFromClasspath(RESULTS_WITH_IDS_PATH);

        final VerificationMethodResults results = CONVERTER.toObject(json, VerificationMethodResults.class);

        final VerificationMethodResults expectedResults = buildResults();
        assertThat(results.getId()).isEqualTo(expectedResults.getId());
        assertThat(results.getContextId()).isEqualTo(expectedResults.getContextId());
        assertThat(results).usingElementComparator(new VerificationMethodResultComparator())
                .containsExactlyElementsOf(expectedResults);
    }

    @Test
    public void shouldDeserializeResultsWithoutIds() {
        final String json = loadContentFromClasspath(RESULTS_WITHOUT_IDS_PATH);

        final VerificationMethodResults results = CONVERTER.toObject(json, VerificationMethodResults.class);

        assertThat(results.getId()).isNull();
        assertThat(results.getContextId()).isNull();
        final VerificationMethodResults expectedResults = buildResults();
        assertThat(results).usingElementComparator(new VerificationMethodResultComparator())
                .containsExactlyElementsOf(expectedResults);
    }

    private static VerificationMethodResults buildResults() {
        final UUID contextId = UUID.fromString("b648e9a6-4a6b-416c-95fb-194d95c0bea8");
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .methodName("PUSH_NOTIFICATION")
                .sequenceName("PUSH_NOTIFICATION")
                .verificationId(UUID.fromString("a7609223-8c80-4e31-9bf8-e75d63cb998f"))
                .timestamp(Instant.parse("2019-03-10T21:51:54.638Z"))
                .result(VerificationResult.SUCCESS)
                .build();
        return VerificationMethodResults.builder()
                .id(UUID.fromString("7936b9b4-9426-41a2-822e-bb9aa957af87"))
                .contextId(contextId)
                .results(Collections.singleton(result))
                .build();
    }

    private static class VerificationMethodResultComparator implements Comparator<VerificationMethodResult> {

        @Override
        public int compare(final VerificationMethodResult r1, final VerificationMethodResult r2) {
            return r1.getContextId().compareTo(r2.getContextId()) +
                    r1.getVerificationId().compareTo(r2.getVerificationId()) +
                    r1.getMethodName().compareTo(r2.getMethodName()) +
                    r1.getSequenceName().compareTo(r2.getSequenceName()) +
                    r1.getTimestamp().compareTo(r2.getTimestamp()) +
                    r1.getResult().compareTo(r2.getResult());
        }

    }

}
