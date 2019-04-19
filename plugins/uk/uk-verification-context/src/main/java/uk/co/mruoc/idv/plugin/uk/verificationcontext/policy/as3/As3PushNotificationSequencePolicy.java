package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.PushNotificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;

public class As3PushNotificationSequencePolicy extends VerificationSequencePolicy {

    public As3PushNotificationSequencePolicy() {
        super(new PushNotificationMethodPolicy());
    }

}
