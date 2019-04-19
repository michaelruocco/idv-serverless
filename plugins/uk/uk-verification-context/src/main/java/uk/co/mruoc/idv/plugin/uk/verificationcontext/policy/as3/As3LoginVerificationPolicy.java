package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.Arrays;
import java.util.Collection;

public class As3LoginVerificationPolicy extends VerificationPolicy {

    private static final Collection<VerificationSequencePolicy> ENTRIES = Arrays.asList(
            new As3PushNotificationSequencePolicy(),
            new As3PhysicalPinsentrySequencePolicy()
    );

    public As3LoginVerificationPolicy() {
        super(Activity.Types.LOGIN, ENTRIES);
    }

}
