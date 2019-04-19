package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PhysicalPinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;

public class RsaPhysicalPinsentrySequencePolicy extends VerificationSequencePolicy {

    public RsaPhysicalPinsentrySequencePolicy() {
        super(new PhysicalPinsentryMethodPolicy(PinsentryFunction.RESPOND));
    }

}
