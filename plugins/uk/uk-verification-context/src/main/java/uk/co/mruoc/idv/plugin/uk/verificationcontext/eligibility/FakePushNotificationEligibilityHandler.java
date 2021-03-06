package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

public class FakePushNotificationEligibilityHandler implements EligibilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.PUSH_NOTIFICATION;

    @Override
    public VerificationMethod loadMethod(final VerificationMethodRequest request) {
        return new PushNotificationVerificationMethod(request.getDuration());
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

}
