package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

@Getter
public class MobilePinsentryMethodPolicy extends VerificationMethodPolicy {

    private final PinsentryFunction function;

    public MobilePinsentryMethodPolicy(final PinsentryFunction function) {
        this(DEFAULT_DURATION, function);
    }

    public MobilePinsentryMethodPolicy(final int duration, final PinsentryFunction function) {
        super(VerificationMethod.Names.MOBILE_PINSENTRY, duration);
        this.function = function;
    }

}
