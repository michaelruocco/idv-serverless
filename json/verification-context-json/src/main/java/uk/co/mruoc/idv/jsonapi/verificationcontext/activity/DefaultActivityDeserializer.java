package uk.co.mruoc.idv.jsonapi.verificationcontext.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class DefaultActivityDeserializer extends StdDeserializer<DefaultActivity> {

    private final ObjectMapper mapper;

    public DefaultActivityDeserializer(final ObjectMapper mapper) {
        super(DefaultActivity.class);
        this.mapper = mapper;
    }

    @Override
    public DefaultActivity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode activityNode = parser.readValueAsTree();
        return toDefaultActivity(activityNode);
    }

    public DefaultActivity toDefaultActivity(final JsonNode activityNode) {
        final String type = extractType(activityNode);
        final Instant timestamp = extractTimestamp(activityNode);
        final Map<String, Object> properties = extractProperties(activityNode);
        return new DefaultActivity(type, timestamp, properties);
    }

    private Map<String, Object> extractProperties(final JsonNode activityNode) {
        final JsonNode propertiesNode = extractPropertiesNode(activityNode);
        final Map<String, Object> properties = mapper.convertValue(propertiesNode, Map.class);
        return Collections.unmodifiableMap(properties);
    }

    private static String extractType(final JsonNode activityNode) {
        return activityNode.get("type").asText();
    }

    private static Instant extractTimestamp(final JsonNode activityNode) {
        return Instant.parse(activityNode.get("timestamp").asText());
    }

    private static JsonNode extractPropertiesNode(final JsonNode node) {
        return node.get("properties");
    }

}
