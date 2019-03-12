package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import static org.assertj.core.api.Assertions.assertThat;

public class PushNotificationMethodPolicyTest {

    private static final int DURATION = 500000;

    private final VerificationMethodPolicy policy = new PushNotificationMethodPolicy(DURATION);

    @Test
    public void shouldReturnMethodName() {
        assertThat(policy.getMethodName()).isEqualTo(VerificationMethod.Names.PUSH_NOTIFICATION);
    }

    @Test
    public void shouldReturnDuration() {
        assertThat(policy.getDuration()).isEqualTo(DURATION);
    }

    @Test
    public void shouldReturnDefaultDurationIfNotSpecified() {
        final VerificationMethodPolicy defaultDurationPolicy = new PushNotificationMethodPolicy();

        assertThat(defaultDurationPolicy.getDuration()).isEqualTo(VerificationMethodPolicy.DEFAULT_DURATION);
    }

}
