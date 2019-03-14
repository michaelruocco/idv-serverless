package uk.co.mruoc.idv.core.verificationcontext.model.policy.rsa;

import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;

public class RsaChannelVerificationPolicies extends ChannelVerificationPolicies {

    public RsaChannelVerificationPolicies() {
        super(Channel.Ids.RSA, new RsaOnlinePurchaseVerificationPolicy());
    }

}
