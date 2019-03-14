package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;

import java.util.Collection;
import java.util.Collections;

public class BbosChannelVerificationPolicies extends ChannelVerificationPolicies {

    public BbosChannelVerificationPolicies() {
        super(Channel.Ids.BBOS, new BbosLoginVerificationPolicy());
    }

    public static class BbosLoginVerificationPolicy extends VerificationPolicy {

        private static final Collection<VerificationMethodPolicyEntry> ENTRIES = Collections.singleton(
                new BbosMobilePinsentryVerificationPolicyEntry()
        );

        public BbosLoginVerificationPolicy() {
            super(Activity.Types.LOGIN, ENTRIES);
        }

    }

    private static class BbosMobilePinsentryVerificationPolicyEntry extends VerificationMethodPolicyEntry {

        private BbosMobilePinsentryVerificationPolicyEntry() {
            super(new MobilePinsentryMethodPolicy(PinsentryFunction.IDENTIFY));
        }

    }

}
