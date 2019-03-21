package uk.co.mruoc.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.model.CardNumber;
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
import uk.co.mruoc.idv.json.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextObjectMapperSingleton;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationMethodDeserializerTest {

    private static final String CARD_CREDENTIALS_METHOD_PATH = "/method/card-credentials-verification-method.json";
    private static final String OTP_SMS_METHOD_PATH = "/method/otp-sms-verification-method.json";
    private static final String PHYSICAL_PINSENTRY_METHOD_PATH = "/method/physical-pinsentry-verification-method.json";
    private static final String MOBILE_PINSENTRY_METHOD_PATH = "/method/mobile-pinsentry-verification-method.json";
    private static final String PUSH_NOTIFICATION_METHOD_PATH = "/method/push-notification-verification-method.json";
    private static final String DEFAULT_METHOD_PATH = "/method/default-verification-method.json";

    private static final int DURATION = 300000;

    private static final ObjectMapper MAPPER = VerificationContextObjectMapperSingleton.get();

    @Test
    public void shouldSerializeCardCredentialsMethod() throws JsonProcessingException {
        final VerificationMethod method = buildCardCredentialsMethod();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = JsonLoader.loadJson(CARD_CREDENTIALS_METHOD_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeCardCredentialsMethod() throws IOException {
        final String json = JsonLoader.loadJson(CARD_CREDENTIALS_METHOD_PATH);

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = buildCardCredentialsMethod();
        assertThat(method).isEqualToComparingFieldByFieldRecursively(expectedMethod);
    }

    @Test
    public void shouldSerializeOtpSmsMethod() throws JsonProcessingException {
        final VerificationMethod method = buildOtpSmsMethod();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = JsonLoader.loadJson(OTP_SMS_METHOD_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeOtpSmsMethod() throws IOException {
        final String json = JsonLoader.loadJson(OTP_SMS_METHOD_PATH);

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = buildOtpSmsMethod();
        assertThat(method).isEqualToComparingFieldByFieldRecursively(expectedMethod);
    }

    @Test
    public void shouldSerializePhysicalPinsentryMethod() throws JsonProcessingException {
        final VerificationMethod method = buildPhysicalPinsentryMethod();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = JsonLoader.loadJson(PHYSICAL_PINSENTRY_METHOD_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializePhysicalPinsentryMethod() throws IOException {
        final String json = JsonLoader.loadJson(PHYSICAL_PINSENTRY_METHOD_PATH);

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = buildPhysicalPinsentryMethod();
        assertThat(method).isEqualToComparingFieldByFieldRecursively(expectedMethod);
    }

    @Test
    public void shouldSerializeMobilePinsentryMethod() throws JsonProcessingException {
        final VerificationMethod method = buildMobilePinsentryMethod();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = JsonLoader.loadJson(MOBILE_PINSENTRY_METHOD_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeMobilePinsentryMethod() throws IOException {
        final String json = JsonLoader.loadJson(MOBILE_PINSENTRY_METHOD_PATH);

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = buildMobilePinsentryMethod();
        assertThat(method).isEqualToComparingFieldByFieldRecursively(expectedMethod);
    }

    @Test
    public void shouldSerializePushNotificationMethod() throws JsonProcessingException {
        final VerificationMethod method = buildPushNotificationMethod();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = JsonLoader.loadJson(PUSH_NOTIFICATION_METHOD_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializePushNotificationMethod() throws IOException {
        final String json = JsonLoader.loadJson(PUSH_NOTIFICATION_METHOD_PATH);

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = buildPushNotificationMethod();
        assertThat(method).isEqualToComparingFieldByFieldRecursively(expectedMethod);
    }

    @Test
    public void shouldSerializeDefaultMethod() throws JsonProcessingException {
        final VerificationMethod method = buildDefaultMethod();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = JsonLoader.loadJson(DEFAULT_METHOD_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeDefaultMethod() throws IOException {
        final String json = JsonLoader.loadJson(DEFAULT_METHOD_PATH);

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = buildDefaultMethod();
        assertThat(method).isEqualToComparingFieldByFieldRecursively(expectedMethod);
    }

    private static VerificationMethod buildCardCredentialsMethod() {
        return new CardCredentialsVerificationMethod(DURATION);
    }

    private static VerificationMethod buildOtpSmsMethod() {
        final Passcode passcode = buildPasscode();
        final Collection<MobileNumber> mobileNumbers = Arrays.asList(
                buildMobileNumber1(),
                buildMobileNumber2()
        );
        return new OtpSmsVerificationMethod(DURATION, passcode, mobileNumbers);
    }

    private static Passcode buildPasscode() {
        return Passcode.builder()
                .attempts(3)
                .duration(150000)
                .length(8)
                .build();
    }

    private static MobileNumber buildMobileNumber1() {
        return MobileNumber.builder()
                .id(UUID.fromString("c3abd98d-655c-48eb-962c-5e72ee9f4257"))
                .masked("********123")
                .build();
    }

    private static MobileNumber buildMobileNumber2() {
        return MobileNumber.builder()
                .id(UUID.fromString("77d84d3f-7924-41ae-a41b-e2a7d8881fda"))
                .masked("********321")
                .build();
    }

    private static VerificationMethod buildPhysicalPinsentryMethod() {
        final PinsentryFunction function = PinsentryFunction.IDENTIFY;
        final Collection<CardNumber> cardNumbers = Arrays.asList(
                buildCardNumber1(),
                buildCardNumber2(),
                buildCardNumber3()
        );
        return new PhysicalPinsentryVerificationMethod(DURATION, function, cardNumbers);
    }

    private static CardNumber buildCardNumber1() {
        return CardNumber.builder()
                .tokenized("4324787978543333")
                .masked("************3333")
                .build();
    }

    private static CardNumber buildCardNumber2() {
        return CardNumber.builder()
                .tokenized("4324787978541234")
                .build();
    }

    private static CardNumber buildCardNumber3() {
        return CardNumber.builder()
                .masked("************4321")
                .build();
    }

    private static VerificationMethod buildMobilePinsentryMethod() {
        final PinsentryFunction function = PinsentryFunction.IDENTIFY;
        return new MobilePinsentryVerificationMethod(DURATION, function);
    }

    private static VerificationMethod buildPushNotificationMethod() {
        final int duration = 300000;
        return new PushNotificationVerificationMethod(duration);
    }

    private static VerificationMethod buildDefaultMethod() {
        final Map<String, Object> subProperty = new HashMap<>();
        subProperty.put("subProperty", "value");

        final Map<String, Object> properties = new HashMap<>();
        properties.put("property", 1);
        properties.put("anotherProperty", Collections.unmodifiableMap(subProperty));

        final String name = "DEFAULT_METHOD";
        return new DefaultVerificationMethod(name, DURATION, Collections.unmodifiableMap(properties));
    }

}
