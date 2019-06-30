package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.lockoutstate.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

public class RsaChannelLockoutPolicies extends ChannelLockoutPolicies {

    public RsaChannelLockoutPolicies() {
        super(UkChannel.Ids.RSA,
                new RsaCreditCardMaxAttemptsLockoutPolicy(),
                new RsaDebitCardMaxAttemptsLockoutPolicy());
    }

}
