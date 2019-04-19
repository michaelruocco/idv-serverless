package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;

@ToString(callSuper = true)
public class PushNotificationVerificationMethod extends DefaultVerificationMethod {

    public PushNotificationVerificationMethod(final int duration) {
        this(duration, DEFAULT_STATUS, DEFAULT_MAX_ATTEMPTS);
    }

    public PushNotificationVerificationMethod(final int duration, final VerificationStatus status, final int maxAttempts) {
        super(Names.PUSH_NOTIFICATION, duration, status, maxAttempts);
    }

}
