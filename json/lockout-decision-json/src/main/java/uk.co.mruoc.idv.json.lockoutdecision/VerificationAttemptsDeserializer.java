package uk.co.mruoc.idv.json.lockoutdecision;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
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
            final JsonNode attempt = attempts.get(0);
            collection.add(toAttempt(attempt));
        }
        return collection;
    }

    private static VerificationAttempt toAttempt(final JsonNode attempt) {
        final VerificationAttempt.VerificationAttemptBuilder builder = VerificationAttempt.builder()
                .channelId(attempt.get("channelId").asText())
                .activityType(attempt.get("activityType").asText())
                .result(attempt.get("result").asText())
                .timestamp(Instant.parse(attempt.get("timestamp").asText()))
                .alias(AliasDeserializer.toAlias(attempt.get("alias")));

        if (attempt.has("methodName")) {
            builder.methodName(attempt.get("methodName").asText());
        }

        return builder.build();
    }

}
