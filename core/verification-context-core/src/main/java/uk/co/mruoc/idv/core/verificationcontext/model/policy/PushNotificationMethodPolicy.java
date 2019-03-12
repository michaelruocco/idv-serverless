package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

public class PushNotificationMethodPolicy extends VerificationMethodPolicy {

    public PushNotificationMethodPolicy(final int duration) {
        super(VerificationMethod.Names.PUSH_NOTIFICATION, duration);
    }

}
