package uk.co.mruoc.idv.core.verificationcontext.model.policy.as3;

import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;

public class As3ChannelVerificationPolicies extends ChannelVerificationPolicies {

    public As3ChannelVerificationPolicies() {
        super(Channel.Ids.AS3, new As3LoginVerificationPolicy());
    }

}
