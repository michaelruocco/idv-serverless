package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaChannelVerificationPoliciesTest {

    private final ChannelVerificationPolicies policies = new RsaChannelVerificationPolicies();

    @Test
    public void shouldApplyToRsaChannel() {
        assertThat(policies.getChannelId()).isEqualTo(UkChannel.Ids.RSA);
    }

    @Test
    public void shouldReturnPolicyForOnlinePurchase() {
        final VerificationPolicy policy = policies.getPolicyFor(Activity.Types.ONLINE_PURCHASE);

        assertThat(policy).isInstanceOf(RsaOnlinePurchaseVerificationPolicy.class);
    }

}
