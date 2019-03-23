package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.channel.UkChannel;

public class As3ChannelVerificationPolicies extends ChannelVerificationPolicies {

    public As3ChannelVerificationPolicies() {
        super(UkChannel.Ids.AS3, new As3LoginVerificationPolicy());
    }

}
