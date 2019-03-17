package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
public class OtpSmsVerificationMethodDeserializer extends StdDeserializer<OtpSmsVerificationMethod> {

    public OtpSmsVerificationMethodDeserializer() {
        super(OtpSmsVerificationMethod.class);
    }

    @Override
    public OtpSmsVerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode methodNode = parser.readValueAsTree();
        return toOtpSms(methodNode);
    }

    public static OtpSmsVerificationMethod toOtpSms(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        final JsonNode propertiesNode = extractPropertiesNode(methodNode);
        final Passcode passcode = extractPasscode(propertiesNode);
        final Collection<MobileNumber> mobileNumbers = extractMobileNumbers(propertiesNode);
        return new OtpSmsVerificationMethod(duration, passcode, mobileNumbers);
    }

    private static int extractDuration(final JsonNode node) {
        return node.get("duration").asInt();
    }

    private static JsonNode extractPropertiesNode(final JsonNode node) {
        return node.get("properties");
    }

    private static Passcode extractPasscode(final JsonNode node) {
        final JsonNode passcodeNode = node.get("passcode");
        final int duration = extractDuration(passcodeNode);
        final int length = passcodeNode.get("length").asInt();
        final int attempts = passcodeNode.get("attempts").asInt();
        return Passcode.builder()
                .duration(duration)
                .length(length)
                .attempts(attempts)
                .build();
    }

    private static Collection<MobileNumber> extractMobileNumbers(final JsonNode node) {
        final Collection<MobileNumber> mobileNumbers = new ArrayList<>();
        final JsonNode mobileNumbersNode = node.get("mobileNumbers");
        for (final JsonNode mobileNumberNode : mobileNumbersNode) {
            mobileNumbers.add(toMobileNumber(mobileNumberNode));
        }
        return mobileNumbers;
    }

    private static MobileNumber toMobileNumber(final JsonNode node) {
        final UUID id = extractId(node);
        final String masked = node.get("masked").asText();
        return MobileNumber.builder()
                .id(id)
                .masked(masked)
                .build();
    }

    private static UUID extractId(final JsonNode node) {
        return UUID.fromString(node.get("id").asText());
    }

}
