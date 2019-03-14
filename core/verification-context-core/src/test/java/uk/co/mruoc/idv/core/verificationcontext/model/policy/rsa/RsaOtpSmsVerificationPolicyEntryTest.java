package uk.co.mruoc.idv.core.verificationcontext.model.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.CardCredentialsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaOtpSmsVerificationPolicyEntryTest {

    private static final int DURATION = 300000;

    private final VerificationMethodPolicyEntry entry = new RsaOtpSmsVerificationPolicyEntry();

    @Test
    public void entryShouldHaveCorrectMethodName() {
        assertThat(entry.getName()).isEqualTo("OTP_SMS");
    }

    @Test
    public void entryShouldContainTwoMethods() {
        assertThat(entry.getMethods()).hasSize(2);
    }

    @Test
    public void policyShouldContainCardCredentialsAsFirstMethod() {
        final CardCredentialsMethodPolicy methodPolicy = (CardCredentialsMethodPolicy) new ArrayList<>(entry.getMethods()).get(0);

        assertThat(methodPolicy.getMethodName()).isEqualTo(VerificationMethod.Names.CARD_CREDENTIALS);
        assertThat(methodPolicy.getDuration()).isEqualTo(DURATION);
    }

    @Test
    public void policyShouldContainOtpSmsAsSecondMethod() {
        final OtpSmsMethodPolicy methodPolicy = (OtpSmsMethodPolicy) new ArrayList<>(entry.getMethods()).get(1);

        assertThat(methodPolicy.getMethodName()).isEqualTo(VerificationMethod.Names.ONE_TIME_PASSCODE_SMS);
        assertThat(methodPolicy.getDuration()).isEqualTo(DURATION);
        assertThat(methodPolicy.getPasscode()).isInstanceOf(RsaPasscode.class);
    }

}
