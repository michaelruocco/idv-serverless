package uk.co.mruoc.idv.json.verificationcontext.result;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class JsonNodeConverter {

    public VerificationMethodResults toVerificationMethodResults(final JsonNode node) {
        return toVerificationMethodResults(node, extractId(node));
    }

    public VerificationMethodResults toVerificationMethodResults(final JsonNode node, final UUID id) {
        final UUID contextId = extractContextId(node);
        return VerificationMethodResults.builder()
                .id(id)
                .contextId(contextId)
                .results(extractResults(node, contextId))
                .build();
    }

    private static UUID extractId(final JsonNode node) {
        if (node.has("id")) {
            return UUID.fromString(node.get("id").asText());
        }
        return null;
    }

    private static UUID extractContextId(final JsonNode node) {
        return extractContextId(node, null);
    }

    private static UUID extractContextId(final JsonNode node, final UUID contextId) {
        if (node.has("contextId")) {
            return UUID.fromString(node.get("contextId").asText());
        }
        return contextId;
    }

    private static Collection<VerificationMethodResult> extractResults(final JsonNode node, final UUID contextId) {
        final Collection<VerificationMethodResult> results = new ArrayList<>();
        final JsonNode resultsNode = node.get("results");
        for (int i = 0; i < resultsNode.size(); i++) {
            results.add(toResult(resultsNode.get(i), contextId));
        }
        return Collections.unmodifiableCollection(results);
    }

    private static VerificationMethodResult toResult(final JsonNode node, final UUID contextId) {
        return VerificationMethodResult.builder()
                .contextId(extractContextId(node, contextId))
                .sequenceName(extractSequenceName(node))
                .methodName(extractMethodName(node))
                .verificationId(extractVerificationId(node))
                .successful(extractSuccessful(node))
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

    private static boolean extractSuccessful(final JsonNode node) {
        return node.get("successful").asBoolean();
    }

    private static Instant extractTimestamp(final JsonNode node) {
        return Instant.parse(node.get("timestamp").asText());
    }

}
