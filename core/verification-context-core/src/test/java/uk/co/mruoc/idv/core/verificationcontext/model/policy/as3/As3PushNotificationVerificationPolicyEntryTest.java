package uk.co.mruoc.idv.core.verificationcontext.model.policy.as3;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class As3PushNotificationVerificationPolicyEntryTest {

    private static final String METHOD_NAME = VerificationMethod.Names.PUSH_NOTIFICATION;
    private static final int DURATION = 300000;

    private final VerificationMethodPolicyEntry entry = new As3PushNotificationPolicyEntry();

    @Test
    public void entryShouldHaveCorrectMethodName() {
        assertThat(entry.getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void entryShouldContainSingleMethod() {
        assertThat(entry.getMethods()).hasSize(1);
    }

    @Test
    public void policyShouldContainPushNotificationMethod() {
        final VerificationMethodPolicy methodPolicy = new ArrayList<>(entry.getMethods()).get(0);

        assertThat(methodPolicy.getMethodName()).isEqualTo(METHOD_NAME);
        assertThat(methodPolicy.getDuration()).isEqualTo(DURATION);
    }

}
