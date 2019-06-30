package uk.co.mruoc.idv.core.lockoutstate.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutstate.model.ChannelLockoutPolicies.LockoutPolicyNotConfiguredForRequestException;

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
    public void shouldThrowExceptionIfNoPolicyConfiguredForRequest() {
        final ChannelLockoutPolicies policies = new ChannelLockoutPolicies(CHANNEL_ID);
        final LockoutStateRequest request = mock(LockoutStateRequest.class);

        final Throwable cause = catchThrowable(() -> policies.getPolicyFor(request));

        assertThat(cause).isInstanceOf(LockoutPolicyNotConfiguredForRequestException.class);
    }

    @Test
    public void shouldReturnPolicyConfiguredForRequest() {
        final LockoutPolicy expectedPolicy = mock(LockoutPolicy.class);
        final ChannelLockoutPolicies policies = new ChannelLockoutPolicies(CHANNEL_ID, expectedPolicy);
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(expectedPolicy.appliesTo(request)).willReturn(true);

        final LockoutPolicy actualPolicy = policies.getPolicyFor(request);

        assertThat(actualPolicy).isEqualTo(expectedPolicy);
    }

}
