package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;

import java.io.IOException;

@Slf4j
public class PushNotificationVerificationMethodDeserializer extends StdDeserializer<PushNotificationVerificationMethod> {

    public PushNotificationVerificationMethodDeserializer() {
        super(PushNotificationVerificationMethod.class);
    }

    @Override
    public PushNotificationVerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode methodNode = parser.readValueAsTree();
        return toPushNotification(methodNode);
    }

    public static PushNotificationVerificationMethod toPushNotification(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        return new PushNotificationVerificationMethod(duration);
    }

    private static int extractDuration(final JsonNode node) {
        return node.get("duration").asInt();
    }

}
