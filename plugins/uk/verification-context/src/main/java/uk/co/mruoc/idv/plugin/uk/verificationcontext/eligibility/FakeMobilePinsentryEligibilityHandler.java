package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.MobilePinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Optional;

public class FakeMobilePinsentryEligibilityHandler implements EligibilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.MOBILE_PINSENTRY;

    @Override
    public Optional<VerificationMethod> loadMethodIfEligible(final EligibleMethodRequest request) {
        final MobilePinsentryMethodPolicy method = (MobilePinsentryMethodPolicy) request.getMethodPolicy();
        final PinsentryFunction function = method.getFunction();
        return Optional.of(new MobilePinsentryVerificationMethod(request.getDuration(), function));
    }

    @Override
    public boolean isSupported(final EligibleMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

}
