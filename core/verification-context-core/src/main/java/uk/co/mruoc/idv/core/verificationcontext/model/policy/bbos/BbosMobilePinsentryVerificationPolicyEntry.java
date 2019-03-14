package uk.co.mruoc.idv.core.verificationcontext.model.policy.bbos;

import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.MobilePinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;

public class BbosMobilePinsentryVerificationPolicyEntry extends VerificationMethodPolicyEntry {

    public BbosMobilePinsentryVerificationPolicyEntry() {
        super(new MobilePinsentryMethodPolicy(PinsentryFunction.IDENTIFY));
    }

}
