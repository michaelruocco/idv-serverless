package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutstate.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class RsaChannelLockoutPoliciesTest {

    private final ChannelLockoutPolicies policies = new RsaChannelLockoutPolicies();

    @Test
    public void shouldReturnChannelId() {
        assertThat(policies.getChannelId()).isEqualTo(UkChannel.Ids.RSA);
    }

    @Test
    public void shouldReturnCreditCardPolicy() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAliasTypeName()).willReturn(AliasType.Names.CREDIT_CARD_NUMBER);

        final LockoutPolicy policy = policies.getPolicyFor(request);

        assertThat(policy).isInstanceOf(RsaCreditCardMaxAttemptsLockoutPolicy.class);
    }

    @Test
    public void shouldReturnDebitCardPolicy() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);

        final LockoutPolicy policy = policies.getPolicyFor(request);

        assertThat(policy).isInstanceOf(RsaDebitCardMaxAttemptsLockoutPolicy.class);
    }

}
