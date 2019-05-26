package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies.LockoutPolicyNotConfiguredForAttemptException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ChannelLockoutPoliciesTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Test
    public void shouldReturnChannelId() {
        final ChannelLockoutPolicies policies = new ChannelLockoutPolicies(CHANNEL_ID);

        assertThat(policies.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    public void shouldThrowExceptionIfNoPolicyConfiguredForAttempt() {
        final ChannelLockoutPolicies policies = new ChannelLockoutPolicies(CHANNEL_ID);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final Throwable cause = catchThrowable(() -> policies.getPolicyFor(attempt));

        assertThat(cause).isInstanceOf(LockoutPolicyNotConfiguredForAttemptException.class);
    }

    @Test
    public void shouldReturnPolicyConfiguredForAttempt() {
        final LockoutPolicy expectedPolicy = mock(LockoutPolicy.class);
        final ChannelLockoutPolicies policies = new ChannelLockoutPolicies(CHANNEL_ID, expectedPolicy);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(expectedPolicy.appliesTo(attempt)).willReturn(true);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final LockoutPolicy actualPolicy = policies.getPolicyFor(attempt);

        assertThat(actualPolicy).isEqualTo(expectedPolicy);
    }

}
