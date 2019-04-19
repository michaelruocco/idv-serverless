package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.Arrays;
import java.util.Collection;

public class RsaOnlinePurchaseVerificationPolicy extends VerificationPolicy {

    private static final Collection<VerificationSequencePolicy> ENTRIES = Arrays.asList(
            new RsaPhysicalPinsentrySequencePolicy(),
            new RsaOtpSmsSequencePolicy()
    );

    public RsaOnlinePurchaseVerificationPolicy() {
        super(Activity.Types.ONLINE_PURCHASE, ENTRIES);
    }

}
