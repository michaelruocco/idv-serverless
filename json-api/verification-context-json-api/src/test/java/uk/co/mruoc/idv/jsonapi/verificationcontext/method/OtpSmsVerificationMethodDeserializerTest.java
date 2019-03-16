package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.jsonapi.verificationcontext.ObjectMapperSingleton;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OtpSmsVerificationMethodDeserializerTest {

    private static final String JSON = JsonLoader.loadJson("/method/otp-sms-verification-method.json");
    private static final VerificationMethod METHOD = buildMethod();
    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeMethod() throws JsonProcessingException {
        final String json = MAPPER.writeValueAsString(METHOD);

        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeMethod() throws IOException {
        final VerificationMethod method = MAPPER.readValue(JSON, OtpSmsVerificationMethod.class);

        assertThat(method).isEqualToComparingFieldByFieldRecursively(METHOD);
    }

    private static VerificationMethod buildMethod() {
        final int duration = 300000;
        final Passcode passcode = buildPasscode();
        final Collection<MobileNumber> mobileNumbers = Arrays.asList(
                buildMobileNumber1(),
                buildMobileNumber2()
        );
        return new OtpSmsVerificationMethod(duration, passcode, mobileNumbers);
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

}
