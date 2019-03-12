package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import static org.assertj.core.api.Assertions.assertThat;

public class MobileNumberMethodPolicyTest {

    private static final int DURATION = 300000;
    private static final PinsentryFunction FUNCTION = PinsentryFunction.IDENTIFY;

    private final MobilePinsentryMethodPolicy policy = new MobilePinsentryMethodPolicy(DURATION, FUNCTION);

    @Test
    public void shouldReturnMethodName() {
        assertThat(policy.getMethodName()).isEqualTo(VerificationMethod.Names.MOBILE_PINSENTRY);
    }

    @Test
    public void shouldReturnDuration() {
        assertThat(policy.getDuration()).isEqualTo(DURATION);
    }

    @Test
    public void shouldReturnFunction() {
        assertThat(policy.getFunction()).isEqualTo(FUNCTION);
    }

}
