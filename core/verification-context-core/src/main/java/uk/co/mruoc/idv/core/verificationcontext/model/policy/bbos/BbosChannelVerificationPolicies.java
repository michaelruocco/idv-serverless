package uk.co.mruoc.idv.core.verificationcontext.model.policy.bbos;

import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;

public class BbosChannelVerificationPolicies extends ChannelVerificationPolicies {

    public BbosChannelVerificationPolicies() {
        super(Channel.Ids.BBOS, new BbosLoginVerificationPolicy());
    }

}
