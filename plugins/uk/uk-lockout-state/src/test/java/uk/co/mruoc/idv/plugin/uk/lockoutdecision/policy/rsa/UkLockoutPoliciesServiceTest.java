package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutPoliciesService;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class UkLockoutPoliciesServiceTest {

    private final LockoutPoliciesService policiesService = new UkLockoutPoliciesService();

    @Test
    public void shouldReturnRsaMaxAttemptsPolicy() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getChannelId()).willReturn(UkChannel.Ids.RSA);
        given(request.getAliasTypeName()).willReturn(AliasType.Names.CREDIT_CARD_NUMBER);
        given(request.getActivityType()).willReturn("ONLINE_PURCHASE");

        final LockoutPolicy policy = policiesService.getPolicy(request);

        assertThat(policy).isInstanceOf(RsaMaxAttemptsLockoutPolicy.class);
    }


    @Test
    public void shouldReturnAs3TimeBasedLockoutPolicy() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getChannelId()).willReturn(UkChannel.Ids.AS3);
        given(request.getActivityType()).willReturn("LOGIN");

        final LockoutPolicy policy = policiesService.getPolicy(request);

        assertThat(policy).isInstanceOf(As3TimeBasedLockoutPolicy.class);
    }

}
