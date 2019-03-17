package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;

import java.io.IOException;

@Slf4j
public class MobilePinsentryVerificationMethodDeserializer extends StdDeserializer<MobilePinsentryVerificationMethod> {

    public MobilePinsentryVerificationMethodDeserializer() {
        super(MobilePinsentryVerificationMethod.class);
    }

    @Override
    public MobilePinsentryVerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode methodNode = parser.readValueAsTree();
        return toMobilePinsentry(methodNode);
    }

    public static MobilePinsentryVerificationMethod toMobilePinsentry(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        final JsonNode propertiesNode = extractPropertiesNode(methodNode);
        final PinsentryFunction function = extractFunction(propertiesNode);
        return new MobilePinsentryVerificationMethod(duration, function);
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

}
