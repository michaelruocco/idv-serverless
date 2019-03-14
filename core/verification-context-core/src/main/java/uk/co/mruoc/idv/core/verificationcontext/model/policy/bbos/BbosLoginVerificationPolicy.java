package uk.co.mruoc.idv.core.verificationcontext.model.policy.bbos;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.Collection;
import java.util.Collections;

public class BbosLoginVerificationPolicy extends VerificationPolicy {

    private static final Collection<VerificationMethodPolicyEntry> ENTRIES = Collections.singleton(
            new BbosMobilePinsentryVerificationPolicyEntry()
    );

    public BbosLoginVerificationPolicy() {
        super(Activity.Types.LOGIN, ENTRIES);
    }

}
