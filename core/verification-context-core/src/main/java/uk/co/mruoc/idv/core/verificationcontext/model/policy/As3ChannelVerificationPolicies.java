package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;

import java.util.Arrays;
import java.util.Collection;

public class As3ChannelVerificationPolicies extends ChannelVerificationPolicies {

    public As3ChannelVerificationPolicies() {
        super(Channel.Ids.AS3, new As3LoginVerificationPolicy());
    }

    public static class As3LoginVerificationPolicy extends VerificationPolicy {

        private static final Collection<VerificationMethodPolicyEntry> ENTRIES = Arrays.asList(
                new As3PushNotificationPolicyEntry(),
                new As3PhysicalPinsentryVerificationPolicyEntry()
        );

        public As3LoginVerificationPolicy() {
            super(Activity.Types.LOGIN, ENTRIES);
        }

    }

    private static class As3PushNotificationPolicyEntry extends VerificationMethodPolicyEntry {

        private As3PushNotificationPolicyEntry() {
            super(new PushNotificationMethodPolicy());
        }

    }

    private static class As3PhysicalPinsentryVerificationPolicyEntry extends VerificationMethodPolicyEntry {

        private As3PhysicalPinsentryVerificationPolicyEntry() {
            super(new PhysicalPinsentryMethodPolicy(PinsentryFunction.IDENTIFY));
        }

    }

}
