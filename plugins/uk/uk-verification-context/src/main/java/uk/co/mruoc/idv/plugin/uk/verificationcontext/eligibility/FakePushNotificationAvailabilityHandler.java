package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import java.util.concurrent.CompletableFuture;

public class FakePushNotificationAvailabilityHandler implements AvailabilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.PUSH_NOTIFICATION;

    @Override
    public CompletableFuture<VerificationMethod> loadMethod(final VerificationMethodRequest request) {
        final VerificationMethod method = new PushNotificationVerificationMethod(request.getDuration());
        return CompletableFuture.completedFuture(method);
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

}
