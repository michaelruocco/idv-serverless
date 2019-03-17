package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class DefaultVerificationMethodDeserializer extends StdDeserializer<DefaultVerificationMethod> {

    private final ObjectMapper mapper;

    public DefaultVerificationMethodDeserializer(final ObjectMapper mapper) {
        super(DefaultVerificationMethod.class);
        this.mapper = mapper;
    }

    @Override
    public DefaultVerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode methodNode = parser.readValueAsTree();
        return toDefaultMethod(methodNode);
    }

    public DefaultVerificationMethod toDefaultMethod(final JsonNode methodNode) {
        final String name = extractName(methodNode);
        final int duration = extractDuration(methodNode);
        final Map<String, Object> properties = toProperties(methodNode);
        return new DefaultVerificationMethod(name, duration, properties);
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    private static int extractDuration(final JsonNode node) {
        return node.get("duration").asInt();
    }

    private Map<String, Object> toProperties(final JsonNode node) {
        final JsonNode propertiesNode = node.get("properties");
        final Map<String, Object> properties = mapper.convertValue(propertiesNode, Map.class);
        return Collections.unmodifiableMap(properties);
    }

}
