package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.lockoutstate.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

public class As3ChannelLockoutPolicies extends ChannelLockoutPolicies {

    public As3ChannelLockoutPolicies() {
        super(UkChannel.Ids.AS3, new As3TimeBasedLockoutPolicy());
    }

}
