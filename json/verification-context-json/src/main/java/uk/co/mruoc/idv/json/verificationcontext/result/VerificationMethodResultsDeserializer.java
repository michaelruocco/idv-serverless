package uk.co.mruoc.idv.json.verificationcontext.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationResult;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Slf4j
public class VerificationMethodResultsDeserializer extends StdDeserializer<VerificationMethodResults> {

    public VerificationMethodResultsDeserializer() {
        super(VerificationMethodResults.class);
    }

    @Override
    public VerificationMethodResults deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.readValueAsTree();
        return VerificationMethodResults.builder()
                .id(extractId(node))
                .contextId(extractContextId(node))
                .results(extractResults(node))
                .build();
    }

    private static UUID extractId(final JsonNode node) {
        return UUID.fromString(node.get("id").asText());
    }

    private static UUID extractContextId(final JsonNode node) {
        return UUID.fromString(node.get("contextId").asText());
    }

    private static Collection<VerificationMethodResult> extractResults(final JsonNode node) {
        final Collection<VerificationMethodResult> results = new ArrayList<>();
        final JsonNode resultsNode = node.get("results");
        for (int i = 0; i < resultsNode.size(); i++) {
            results.add(toResult(resultsNode.get(i)));
        }
        return Collections.unmodifiableCollection(results);
    }

    private static VerificationMethodResult toResult(final JsonNode node) {
        System.out.println(node);
        return VerificationMethodResult.builder()
                .contextId(extractContextId(node))
                .sequenceName(extractSequenceName(node))
                .methodName(extractMethodName(node))
                .verificationId(extractVerificationId(node))
                .result(extractResult(node))
                .timestamp(extractTimestamp(node))
                .build();
    }

    private static String extractSequenceName(final JsonNode node) {
        return node.get("sequence").asText();
    }

    private static String extractMethodName(final JsonNode node) {
        return node.get("method").asText();
    }

    private static UUID extractVerificationId(final JsonNode node) {
        return UUID.fromString(node.get("verificationId").asText());
    }

    private static VerificationResult extractResult(final JsonNode node) {
        return VerificationResult.valueOf(node.get("result").asText());
    }

    private static Instant extractTimestamp(final JsonNode node) {
        return Instant.parse(node.get("timestamp").asText());
    }

}
