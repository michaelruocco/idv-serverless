package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.plugin.uk.channel.UkChannel;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class RsaChannelLockoutPoliciesTest {

    private final RsaChannelLockoutPolicies policies = new RsaChannelLockoutPolicies();

    @Test
    public void shouldReturnRsaChannelId() {
        assertThat(policies.getChannelId()).isEqualTo(UkChannel.Ids.RSA);
    }

    @Test
    public void shouldReturnRsaMaxAttemptsPolicyForRsaChannel() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getMethodName()).willReturn(Optional.of("ANY"));
        given(attempt.getAliasTypeName()).willReturn(AliasType.Names.CREDIT_CARD_NUMBER);

        final LockoutPolicy policy = policies.getPolicyFor(attempt);

        assertThat(policy).isInstanceOf(RsaMaxAttemptsLockoutPolicy.class);
    }

}
