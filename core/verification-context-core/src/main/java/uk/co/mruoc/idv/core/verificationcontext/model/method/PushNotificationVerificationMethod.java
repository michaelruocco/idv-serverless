package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;

@ToString(callSuper = true)
public class PushNotificationVerificationMethod extends DefaultVerificationMethod {

    public PushNotificationVerificationMethod(final int duration) {
        super(Names.PUSH_NOTIFICATION, duration);
    }

}
