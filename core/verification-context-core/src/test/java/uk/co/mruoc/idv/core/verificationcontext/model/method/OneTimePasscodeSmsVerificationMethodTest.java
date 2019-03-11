package uk.co.mruoc.idv.core.verificationcontext.model.method;

import org.junit.Test;
import uk.co.mruoc.idv.core.model.MobileNumber;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OneTimePasscodeSmsVerificationMethodTest {

    private static final int DURATION = 300000;

    private static final Passcode PASSCODE = Passcode.builder()
            .length(8)
            .duration(30000)
            .attempts(3)
            .build();

    private static final Collection<MobileNumber> MOBILE_NUMBERS = Arrays.asList(
            MobileNumber.builder().id(UUID.randomUUID()).masked("********111").build(),
            MobileNumber.builder().id(UUID.randomUUID()).masked("********333").build()
    );

    private final OneTimePasscodeSmsVerificationMethod otpSms = new OneTimePasscodeSmsVerificationMethod(DURATION, PASSCODE, MOBILE_NUMBERS);

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
    public void shouldReturnPasscode() {
        final Passcode passcode = otpSms.getPasscode();

        assertThat(passcode).isEqualTo(PASSCODE);
    }
    @Test
    public void shouldReturnMobileNumbers() {
        final Collection<MobileNumber> mobileNumbers = otpSms.getMobileNumbers();

        assertThat(mobileNumbers).isEqualTo(MOBILE_NUMBERS);
    }

}
