package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.PushNotificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;

public class As3PushNotificationPolicyEntry extends VerificationMethodPolicyEntry {

    public As3PushNotificationPolicyEntry() {
        super(new PushNotificationMethodPolicy());
    }

}
