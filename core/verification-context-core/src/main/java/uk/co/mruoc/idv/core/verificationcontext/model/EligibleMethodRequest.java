package uk.co.mruoc.idv.core.verificationcontext.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;

@Builder
@ToString
@Getter
public class EligibleMethodRequest {

    private final Channel channel;
    private final Alias inputAlias;
    private final Identity identity;
    private final VerificationMethodPolicy methodPolicy;

    public String getChannelId() {
        return channel.getId();
    }

    public String getMethodName() {
        return methodPolicy.getMethodName();
    }

    public int getDuration() {
        return methodPolicy.getDuration();
    }

}
