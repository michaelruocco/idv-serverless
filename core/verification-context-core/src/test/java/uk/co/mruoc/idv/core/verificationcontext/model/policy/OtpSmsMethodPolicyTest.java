package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class OtpSmsMethodPolicyTest {

    private static final int DURATION = 500000;
    private static final Passcode PASSCODE = mock(Passcode.class);

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

    @Test
    public void shouldReturnDefaultDurationIfNotSpecified() {
        final VerificationMethodPolicy defaultDurationPolicy = new OtpSmsMethodPolicy(PASSCODE);

        assertThat(defaultDurationPolicy.getDuration()).isEqualTo(VerificationMethodPolicy.DEFAULT_DURATION);
    }

}
