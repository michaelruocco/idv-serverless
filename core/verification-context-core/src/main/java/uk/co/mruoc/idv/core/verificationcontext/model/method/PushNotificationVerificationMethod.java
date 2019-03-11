package uk.co.mruoc.idv.core.verificationcontext.model.method;

public class PushNotificationVerificationMethod extends DefaultVerificationMethod {

    public PushNotificationVerificationMethod(final int duration) {
        super(Names.PUSH_NOTIFICATION, duration);
    }

}
