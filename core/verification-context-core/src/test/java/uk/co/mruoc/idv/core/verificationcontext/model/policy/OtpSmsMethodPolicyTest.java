package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import static org.assertj.core.api.Assertions.assertThat;

public class OtpSmsMethodPolicyTest {

    private static final int DURATION = 300000;
    private static final Passcode PASSCODE = Passcode.builder()
            .attempts(4)
            .length(8)
            .duration(150000)
            .build();

    private final OtpSmsMethodPolicy policy = new OtpSmsMethodPolicy(DURATION, PASSCODE);

    @Test
    public void shouldReturnMethodName() {
        assertThat(policy.getMethodName()).isEqualTo(VerificationMethod.Names.ONE_TIME_PASSCODE_SMS);
    }

    @Test
    public void shouldReturnDuration() {
        assertThat(policy.getDuration()).isEqualTo(DURATION);
    }

    @Test
    public void shouldReturnPasscode() {
        assertThat(policy.getPasscode()).isEqualTo(PASSCODE);
    }

}
