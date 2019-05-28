package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutPoliciesService;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import static org.assertj.core.api.Assertions.assertThat;

public class UkLockoutPoliciesServiceTest {

    private final LockoutPoliciesService policiesService = new UkLockoutPoliciesService();

    @Test
    public void shouldReturnRsaChannelLockoutPoliciesForRsaChannel() {
        final ChannelLockoutPolicies policies = policiesService.getPoliciesForChannel(UkChannel.Ids.RSA);

        assertThat(policies).isInstanceOf(RsaChannelLockoutPolicies.class);
    }

}
