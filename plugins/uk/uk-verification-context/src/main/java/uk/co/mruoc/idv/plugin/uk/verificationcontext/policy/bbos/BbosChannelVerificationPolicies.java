package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.bbos;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

public class BbosChannelVerificationPolicies extends ChannelVerificationPolicies {

    public BbosChannelVerificationPolicies() {
        super(UkChannel.Ids.BBOS, new BbosLoginVerificationPolicy());
    }

}
