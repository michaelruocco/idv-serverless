package uk.co.mruoc.idv.json.authorizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;

import java.io.IOException;

@Slf4j
public class TokenRequestDeserializer extends StdDeserializer<GenerateTokenRequest> {

    public TokenRequestDeserializer() {
        super(GenerateTokenRequest.class);
    }

    @Override
    public GenerateTokenRequest deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.readValueAsTree();
        return DefaultGenerateTokenRequest.builder()
                .subject(extractSubject(node))
                .validForSeconds(extractValidForSeconds(node))
                .build();
    }

    private static String extractSubject(final JsonNode node) {
            return node.get("subject").asText();
    }

    private static Long extractValidForSeconds(final JsonNode node) {
        if (node.has("validForSeconds")) {
            return node.get("validForSeconds").asLong();
        }
        return null;
    }

}
