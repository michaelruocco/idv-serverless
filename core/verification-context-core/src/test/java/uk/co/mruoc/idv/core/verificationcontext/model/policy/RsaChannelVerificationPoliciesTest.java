package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.RsaChannelVerificationPolicies.RsaOnlinePurchaseVerificationPolicy;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaChannelVerificationPoliciesTest {

    private final ChannelVerificationPolicies policies = new RsaChannelVerificationPolicies();

    @Test
    public void shouldApplyToRsaChannel() {
        assertThat(policies.appliesToChannel(Channel.Ids.RSA)).isTrue();
        assertThat(policies.appliesToChannel("ANY_OTHER_CHANNEL")).isFalse();
    }

    @Test
    public void shouldReturnPolicyForOnlinePurchase() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.ONLINE_PURCHASE);

        assertThat(policy.isPresent()).isTrue();
        assertThat(policy.get()).isInstanceOf(RsaOnlinePurchaseVerificationPolicy.class);
    }

}
