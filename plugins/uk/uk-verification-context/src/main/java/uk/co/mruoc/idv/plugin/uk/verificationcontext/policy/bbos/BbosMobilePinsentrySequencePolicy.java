package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.bbos;

import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.MobilePinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;

public class BbosMobilePinsentrySequencePolicy extends VerificationSequencePolicy {

    public BbosMobilePinsentrySequencePolicy() {
        super(new MobilePinsentryMethodPolicy(PinsentryFunction.IDENTIFY));
    }

}
