package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import static org.assertj.core.api.Assertions.assertThat;

public class As3ChannelVerificationPoliciesTest {

    private final ChannelVerificationPolicies policies = new As3ChannelVerificationPolicies();

    @Test
    public void shouldApplyToAs3Channel() {
        assertThat(policies.getChannelId()).isEqualTo(UkChannel.Ids.AS3);
    }

    @Test
    public void shouldReturnPolicyForLogin() {
        final VerificationPolicy policy = policies.getPolicyFor(Activity.Types.LOGIN);

        assertThat(policy).isInstanceOf(As3LoginVerificationPolicy.class);
    }

}
