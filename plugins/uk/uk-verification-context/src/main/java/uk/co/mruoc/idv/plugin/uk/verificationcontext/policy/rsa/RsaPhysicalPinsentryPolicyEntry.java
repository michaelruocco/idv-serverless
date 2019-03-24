package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PhysicalPinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;

public class RsaPhysicalPinsentryPolicyEntry extends VerificationMethodPolicyEntry {

    public RsaPhysicalPinsentryPolicyEntry() {
        super(new PhysicalPinsentryMethodPolicy(PinsentryFunction.RESPOND));
    }

}
