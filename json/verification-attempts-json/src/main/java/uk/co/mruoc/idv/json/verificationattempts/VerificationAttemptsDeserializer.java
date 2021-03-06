package uk.co.mruoc.idv.json.verificationattempts;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.json.identity.AliasDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
public class VerificationAttemptsDeserializer extends StdDeserializer<VerificationAttempts> {

    public VerificationAttemptsDeserializer() {
        super(VerificationAttempts.class);
    }

    @Override
    public VerificationAttempts deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode attemptsNode = parser.readValueAsTree();
        return VerificationAttempts.builder()
                .attempts(toAttempts(attemptsNode.get("attempts")))
                .idvId(UUID.fromString(attemptsNode.get("idvId").asText()))
                .lockoutStateId(UUID.fromString(attemptsNode.get("lockoutStateId").asText()))
                .build();
    }

    private static Collection<VerificationAttempt> toAttempts(final JsonNode attempts) {
        final Collection<VerificationAttempt> collection = new ArrayList<>();
        for (int i = 0; i < attempts.size(); i++) {
            final JsonNode attempt = attempts.get(i);
            collection.add(toAttempt(attempt));
        }
        return collection;
    }

    private static VerificationAttempt toAttempt(final JsonNode attempt) {
        final VerificationAttempt.VerificationAttemptBuilder builder = VerificationAttempt.builder()
                .contextId(UUID.fromString(attempt.get("contextId").asText()))
                .channelId(attempt.get("channelId").asText())
                .activityType(attempt.get("activityType").asText())
                .successful(attempt.get("successful").asBoolean())
                .timestamp(Instant.parse(attempt.get("timestamp").asText()))
                .alias(AliasDeserializer.toAlias(attempt.get("alias")))
                .verificationId(UUID.fromString(attempt.get("verificationId").asText()));

        if (attempt.has("methodName")) {
            builder.methodName(attempt.get("methodName").asText());
        }

        return builder.build();
    }

}
