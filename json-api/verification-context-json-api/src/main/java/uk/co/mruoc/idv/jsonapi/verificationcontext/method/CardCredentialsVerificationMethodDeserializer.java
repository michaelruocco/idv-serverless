package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import java.io.IOException;

@Slf4j
public class CardCredentialsVerificationMethodDeserializer extends StdDeserializer<CardCredentialsVerificationMethod> {

    public CardCredentialsVerificationMethodDeserializer() {
        super(CardCredentialsVerificationMethod.class);
    }

    @Override
    public CardCredentialsVerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode methodNode = parser.readValueAsTree();
        return toCardCredentials(methodNode);
    }

    public static CardCredentialsVerificationMethod toCardCredentials(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        return new CardCredentialsVerificationMethod(duration);
    }

    private static int extractDuration(final JsonNode node) {
        return node.get("duration").asInt();
    }

}
