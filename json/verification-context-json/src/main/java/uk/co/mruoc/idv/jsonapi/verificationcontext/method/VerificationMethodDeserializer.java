package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.model.CardNumber.CardNumberBuilder;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class VerificationMethodDeserializer extends StdDeserializer<VerificationMethod> {

    private final ObjectMapper mapper;

    public VerificationMethodDeserializer(final ObjectMapper mapper) {
        super(VerificationMethod.class);
        this.mapper = mapper;
    }

    @Override
    public VerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode methodNode = parser.readValueAsTree();
        return toMethod(methodNode);
    }

    public VerificationMethod toMethod(final JsonNode methodNode) {
        final String name = extractName(methodNode);
        switch (name) {
            case VerificationMethod.Names.CARD_CREDENTIALS:
                return toCardCredentials(methodNode);
            case VerificationMethod.Names.ONE_TIME_PASSCODE_SMS:
                return toOtpSms(methodNode);
            case VerificationMethod.Names.PUSH_NOTIFICATION:
                return toPushNotification(methodNode);
            case VerificationMethod.Names.PHYSICAL_PINSENTRY:
                return toPhysicalPinsentry(methodNode);
            case VerificationMethod.Names.MOBILE_PINSENTRY:
                return toMobilePinsentry(methodNode);
            default:
                return toDefaultMethod(methodNode);
        }
    }

    private static CardCredentialsVerificationMethod toCardCredentials(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        return new CardCredentialsVerificationMethod(duration);
    }

    private DefaultVerificationMethod toDefaultMethod(final JsonNode methodNode) {
        final String name = extractName(methodNode);
        final int duration = extractDuration(methodNode);
        final Map<String, Object> properties = toProperties(methodNode);
        return new DefaultVerificationMethod(name, duration, properties);
    }

    private static OtpSmsVerificationMethod toOtpSms(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        final JsonNode propertiesNode = extractPropertiesNode(methodNode);
        final Passcode passcode = extractPasscode(propertiesNode);
        final Collection<MobileNumber> mobileNumbers = extractMobileNumbers(propertiesNode);
        return new OtpSmsVerificationMethod(duration, passcode, mobileNumbers);
    }

    private static PhysicalPinsentryVerificationMethod toPhysicalPinsentry(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        final JsonNode propertiesNode = extractPropertiesNode(methodNode);
        final PinsentryFunction function = extractFunction(propertiesNode);
        final Collection<CardNumber> cardNumbers = extractCardNumbers(propertiesNode);
        return new PhysicalPinsentryVerificationMethod(duration, function, cardNumbers);
    }

    private static MobilePinsentryVerificationMethod toMobilePinsentry(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        final JsonNode propertiesNode = extractPropertiesNode(methodNode);
        final PinsentryFunction function = extractFunction(propertiesNode);
        return new MobilePinsentryVerificationMethod(duration, function);
    }

    private static PushNotificationVerificationMethod toPushNotification(final JsonNode methodNode) {
        final int duration = extractDuration(methodNode);
        return new PushNotificationVerificationMethod(duration);
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
        final CardNumberBuilder builder = CardNumber.builder();
        if (node.has("tokenized")) {
            builder.tokenized(node.get("tokenized").asText());
        }
        if (node.has("masked")) {
            builder.masked(node.get("masked").asText());
        }
        return builder.build();
    }

    private Map<String, Object> toProperties(final JsonNode node) {
        final JsonNode propertiesNode = node.get("properties");
        final Map<String, Object> properties = mapper.convertValue(propertiesNode, Map.class);
        return Collections.unmodifiableMap(properties);
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

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    private static int extractDuration(final JsonNode node) {
        return node.get("duration").asInt();
    }

    private static JsonNode extractPropertiesNode(final JsonNode node) {
        return node.get("properties");
    }

    private static UUID extractId(final JsonNode node) {
        return UUID.fromString(node.get("id").asText());
    }

    private static PinsentryFunction extractFunction(final JsonNode node) {
        return PinsentryFunction.valueOf(node.get("function").asText());
    }

}
