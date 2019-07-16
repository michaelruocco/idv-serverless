package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.MobilePinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import java.util.concurrent.CompletableFuture;

public class FakeMobilePinsentryAvailabilityHandler implements AvailabilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.MOBILE_PINSENTRY;

    @Override
    public CompletableFuture<VerificationMethod> loadMethod(final VerificationMethodRequest request) {
        final MobilePinsentryMethodPolicy methodPolicy = (MobilePinsentryMethodPolicy) request.getMethodPolicy();
        final PinsentryFunction function = methodPolicy.getFunction();
        final VerificationMethod method = new MobilePinsentryVerificationMethod(request.getDuration(), function);
        return CompletableFuture.completedFuture(method);
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

}
