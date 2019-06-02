package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class As3ChannelLockoutPoliciesTest {

    private final ChannelLockoutPolicies policies = new As3ChannelLockoutPolicies();

    @Test
    public void shouldReturnChannelId() {
        assertThat(policies.getChannelId()).isEqualTo(UkChannel.Ids.AS3);
    }

    @Test
    public void shouldReturnPolicies() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getMethodName()).willReturn(Optional.of("ANY"));
        given(attempt.getAliasTypeName()).willReturn(AliasType.Names.CREDIT_CARD_NUMBER);

        final LockoutPolicy policy = policies.getPolicyFor(attempt);

        assertThat(policy).isInstanceOf(As3TimeBasedLockoutPolicy.class);
    }

}
