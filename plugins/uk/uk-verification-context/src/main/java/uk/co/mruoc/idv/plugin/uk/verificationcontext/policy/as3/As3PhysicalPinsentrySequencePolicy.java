package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PhysicalPinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;

public class As3PhysicalPinsentrySequencePolicy extends VerificationSequencePolicy {

    public As3PhysicalPinsentrySequencePolicy() {
        super(new PhysicalPinsentryMethodPolicy(PinsentryFunction.IDENTIFY));
    }

}
