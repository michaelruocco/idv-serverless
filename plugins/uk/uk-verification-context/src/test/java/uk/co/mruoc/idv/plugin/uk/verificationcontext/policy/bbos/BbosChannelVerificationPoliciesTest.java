package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.bbos;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import static org.assertj.core.api.Assertions.assertThat;

public class BbosChannelVerificationPoliciesTest {

    private final ChannelVerificationPolicies policies = new BbosChannelVerificationPolicies();

    @Test
    public void shouldApplyToBbosChannel() {
        assertThat(policies.getChannelId()).isEqualTo(UkChannel.Ids.BBOS);
    }

    @Test
    public void shouldReturnPolicyForLogin() {
        final VerificationPolicy policy = policies.getPolicyFor(Activity.Types.LOGIN);

        assertThat(policy).isInstanceOf(BbosLoginVerificationPolicy.class);
    }

}
