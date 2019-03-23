package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Optional;

public class FakePushNotificationEligibilityHandler implements EligibilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.PUSH_NOTIFICATION;

    @Override
    public Optional<VerificationMethod> loadMethodIfEligible(final EligibleMethodRequest request) {
        return Optional.of(new PushNotificationVerificationMethod(request.getDuration()));
    }

    @Override
    public boolean isSupported(final EligibleMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

}
