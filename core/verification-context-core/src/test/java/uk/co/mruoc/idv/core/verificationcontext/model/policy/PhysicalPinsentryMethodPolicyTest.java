package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import static org.assertj.core.api.Assertions.assertThat;

public class PhysicalPinsentryMethodPolicyTest {

    private static final int DURATION = 300000;
    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    private final PhysicalPinsentryMethodPolicy policy = new PhysicalPinsentryMethodPolicy(DURATION, FUNCTION);

    @Test
    public void shouldReturnMethodName() {
        assertThat(policy.getMethodName()).isEqualTo(VerificationMethod.Names.PHYSICAL_PINSENTRY);
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
