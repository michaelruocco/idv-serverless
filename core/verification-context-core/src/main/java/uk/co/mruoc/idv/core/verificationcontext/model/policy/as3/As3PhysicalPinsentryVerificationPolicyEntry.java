package uk.co.mruoc.idv.core.verificationcontext.model.policy.as3;

import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PhysicalPinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;

public class As3PhysicalPinsentryVerificationPolicyEntry extends VerificationMethodPolicyEntry {

    public As3PhysicalPinsentryVerificationPolicyEntry() {
        super(new PhysicalPinsentryMethodPolicy(PinsentryFunction.IDENTIFY));
    }

}
