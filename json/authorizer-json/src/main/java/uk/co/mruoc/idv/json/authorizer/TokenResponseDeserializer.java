package uk.co.mruoc.idv.json.authorizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.authorizer.model.DefaultTokenResponse;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;

import java.io.IOException;

@Slf4j
public class TokenResponseDeserializer extends StdDeserializer<TokenResponse> {

    public TokenResponseDeserializer() {
        super(TokenResponse.class);
    }

    @Override
    public TokenResponse deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.readValueAsTree();
        return DefaultTokenResponse.builder()
                .token(extractToken(node))
                .build();
    }

    private static String extractToken(final JsonNode node) {
        return node.get("token").asText();
    }

}
