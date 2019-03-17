package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class PhysicalPinsentryVerificationMethodDeserializer extends StdDeserializer<PhysicalPinsentryVerificationMethod> {

    public PhysicalPinsentryVerificationMethodDeserializer() {
        super(PhysicalPinsentryVerificationMethod.class);
    }

    @Override
    public PhysicalPinsentryVerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode methodNode = parser.readValueAsTree();
        return toPhysicalPinsentry(methodNode);
    }

    public static PhysicalPinsentryVerificationMethod toPhysicalPinsentry(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        final JsonNode propertiesNode = extractPropertiesNode(methodNode);
        final PinsentryFunction function = extractFunction(propertiesNode);
        final Collection<CardNumber> cardNumbers = extractCardNumbers(propertiesNode);
        return new PhysicalPinsentryVerificationMethod(duration, function, cardNumbers);
    }

    private static int extractDuration(final JsonNode node) {
        return node.get("duration").asInt();
    }

    private static JsonNode extractPropertiesNode(final JsonNode node) {
        return node.get("properties");
    }

    private static PinsentryFunction extractFunction(final JsonNode node) {
        return PinsentryFunction.valueOf(node.get("function").asText());
    }

    private static Collection<CardNumber> extractCardNumbers(final JsonNode node) {
        final List<CardNumber> cardNumbers = new ArrayList<>();
        final JsonNode cardNumbersNode = node.get("cardNumbers");
        for (final JsonNode cardNumberNode : cardNumbersNode) {
            cardNumbers.add(toCardNumber(cardNumberNode));
        }
        return cardNumbers;
    }

    private static CardNumber toCardNumber(final JsonNode node) {
        final String tokenized = node.get("tokenized").asText();
        final String masked = node.get("masked").asText();
        return CardNumber.builder()
                .tokenized(tokenized)
                .masked(masked)
                .build();
    }

}
