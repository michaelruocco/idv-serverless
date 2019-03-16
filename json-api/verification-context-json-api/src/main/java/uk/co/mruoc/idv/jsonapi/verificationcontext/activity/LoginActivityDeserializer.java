package uk.co.mruoc.idv.jsonapi.verificationcontext.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;

import java.io.IOException;
import java.time.Instant;

@Slf4j
public class LoginActivityDeserializer extends StdDeserializer<LoginActivity> {

    public LoginActivityDeserializer() {
        super(LoginActivity.class);
    }

    @Override
    public LoginActivity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode activityNode = parser.readValueAsTree();
        return toLoginActivity(activityNode);
    }

    public static LoginActivity toLoginActivity(final JsonNode activityNode) {
        final Instant timestamp = extractTimestamp(activityNode);
        return new LoginActivity(timestamp);
    }

    private static Instant extractTimestamp(final JsonNode activityNode) {
        return Instant.parse(activityNode.get("timestamp").asText());
    }

}
