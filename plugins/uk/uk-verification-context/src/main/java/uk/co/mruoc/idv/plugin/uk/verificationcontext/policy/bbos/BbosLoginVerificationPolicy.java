package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.bbos;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.Collection;
import java.util.Collections;

public class BbosLoginVerificationPolicy extends VerificationPolicy {

    private static final Collection<VerificationSequencePolicy> ENTRIES = Collections.singleton(
            new BbosMobilePinsentrySequencePolicy()
    );

    public BbosLoginVerificationPolicy() {
        super(Activity.Types.LOGIN, ENTRIES);
    }

}
