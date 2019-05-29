package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;

@ToString(callSuper = true)
public class PushNotificationVerificationMethod extends DefaultVerificationMethod {

    public PushNotificationVerificationMethod(final int duration) {
        this(duration, DEFAULT_STATUS);
    }

    public PushNotificationVerificationMethod(final int duration, final VerificationStatus status) {
        super(Names.PUSH_NOTIFICATION, duration, status);
    }

}
