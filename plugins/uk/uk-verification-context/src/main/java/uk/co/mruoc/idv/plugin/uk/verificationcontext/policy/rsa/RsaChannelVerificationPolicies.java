package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.channel.UkChannel;

public class RsaChannelVerificationPolicies extends ChannelVerificationPolicies {

    public RsaChannelVerificationPolicies() {
        super(UkChannel.Ids.RSA, new RsaOnlinePurchaseVerificationPolicy());
    }

}
