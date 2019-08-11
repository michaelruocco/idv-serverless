package uk.co.mruoc.idv.core.verificationcontext.model.method;

import org.junit.Test;
import uk.co.mruoc.idv.core.model.MobileNumber;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OtpSmsVerificationMethodTest {

    private static final int DURATION = 300000;
    private static final Passcode PASSCODE = Passcode.builder()
            .maxAttempts(3)
            .length(8)
            .duration(150000)
            .build();

    private static final Collection<MobileNumber> MOBILE_NUMBERS = Arrays.asList(
            MobileNumber.builder().id(UUID.fromString("3cc9109d-4a7c-424d-9f7b-5805636dfb2d")).masked("********111").build(),
            MobileNumber.builder().id(UUID.fromString("03571bf0-1140-4ee6-b9a7-49b64e174730")).masked("********333").build()
    );

    private final OtpSmsVerificationMethod otpSms = new OtpSmsVerificationMethod(DURATION, PASSCODE, MOBILE_NUMBERS);

    @Test
    public void shouldReturnName() {
        final String name = otpSms.getName();

        assertThat(name).isEqualTo(VerificationMethod.Names.ONE_TIME_PASSCODE_SMS);
    }

    @Test
    public void shouldReturnDuration() {
        final int duration = otpSms.getDuration();

        assertThat(duration).isEqualTo(DURATION);
    }

    @Test
    public void shouldReturnIsEligible() {
        final boolean eligible = otpSms.isEligible();

        assertThat(eligible).isTrue();
    }

    @Test
    public void shouldReturnPasscode() {
        final Passcode passcode = otpSms.getPasscode();

        assertThat(passcode).isEqualTo(PASSCODE);
    }

    @Test
    public void shouldReturnMobileNumbers() {
        final Collection<MobileNumber> mobileNumbers = otpSms.getMobileNumbers();

        assertThat(mobileNumbers).isEqualTo(MOBILE_NUMBERS);
    }

    @Test
    public void shouldPrintAllValues() {
        final String value = otpSms.toString();

        assertThat(value).isEqualTo("OtpSmsVerificationMethod(super=" +
                "DefaultVerificationMethod(name=ONE_TIME_PASSCODE_SMS, duration=300000, " +
                "eligible=true, " +
                "properties={mobileNumbers=[MobileNumber(id=3cc9109d-4a7c-424d-9f7b-5805636dfb2d, masked=********111), " +
                "MobileNumber(id=03571bf0-1140-4ee6-b9a7-49b64e174730, masked=********333)], " +
                "passcode=Passcode(length=8, duration=150000, maxAttempts=3)}))");
    }

}
