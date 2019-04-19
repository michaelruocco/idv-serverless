package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PhysicalPinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaPhysicalPinsentrySequencePolicyTest {

    private static final String METHOD_NAME = VerificationMethod.Names.PHYSICAL_PINSENTRY;
    private static final int DURATION = 300000;

    private final VerificationSequencePolicy entry = new RsaPhysicalPinsentrySequencePolicy();

    @Test
    public void entryShouldHaveCorrectMethodName() {
        assertThat(entry.getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void entryShouldContainSingleMethod() {
        assertThat(entry.getMethods()).hasSize(1);
    }

    @Test
    public void policyShouldContainPhysicalPinsentryRespondMethod() {
        final PhysicalPinsentryMethodPolicy methodPolicy = (PhysicalPinsentryMethodPolicy) new ArrayList<>(entry.getMethods()).get(0);

        assertThat(methodPolicy.getMethodName()).isEqualTo(METHOD_NAME);
        assertThat(methodPolicy.getDuration()).isEqualTo(DURATION);
        assertThat(methodPolicy.getFunction()).isEqualTo(PinsentryFunction.RESPOND);
    }

}
