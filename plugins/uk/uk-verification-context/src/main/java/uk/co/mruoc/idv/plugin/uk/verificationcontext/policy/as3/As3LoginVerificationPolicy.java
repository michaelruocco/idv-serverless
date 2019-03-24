package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.Arrays;
import java.util.Collection;

public class As3LoginVerificationPolicy extends VerificationPolicy {

    private static final Collection<VerificationMethodPolicyEntry> ENTRIES = Arrays.asList(
            new As3PushNotificationPolicyEntry(),
            new As3PhysicalPinsentryVerificationPolicyEntry()
    );

    public As3LoginVerificationPolicy() {
        super(Activity.Types.LOGIN, ENTRIES);
    }

}
