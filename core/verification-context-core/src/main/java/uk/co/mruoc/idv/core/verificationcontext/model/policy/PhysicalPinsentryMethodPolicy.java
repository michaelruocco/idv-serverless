package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

@Getter
public class PhysicalPinsentryMethodPolicy extends VerificationMethodPolicy {

    private final PinsentryFunction function;

    public PhysicalPinsentryMethodPolicy(final int duration, final PinsentryFunction function) {
        super(VerificationMethod.Names.PHYSICAL_PINSENTRY, duration);
        this.function = function;
    }

}
