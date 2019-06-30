package uk.co.mruoc.idv.core.lockoutstate.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutstate.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutPoliciesService.LockoutPolicyNotConfiguredForChannelException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class DefaultLockoutPoliciesServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final ChannelLockoutPolicies channelPolicies = buildChannelLockoutPolicies();

    private final LockoutPoliciesService service = new DefaultLockoutPoliciesService(Collections.singleton(channelPolicies));

    @Test
    public void shouldThrowExceptionIfPoliciesNoConfiguredForChannel() {
        final String anotherChannelId = "ANOTHER_CHANNEL_ID";
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getChannelId()).willReturn(anotherChannelId);

        final Throwable cause = catchThrowable(() -> service.getPolicy(request));

        assertThat(cause).isInstanceOf(LockoutPolicyNotConfiguredForChannelException.class)
                .hasMessage(anotherChannelId);
    }

    @Test
    public void shouldThrowExceptionIfChannelDoesNotHavePolicyThatAppliesToRequest() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getChannelId()).willReturn(CHANNEL_ID);
        doThrow(LockoutPolicyNotConfiguredForChannelException.class).when(channelPolicies).getPolicyFor(request);

        final Throwable cause = catchThrowable(() -> service.getPolicy(request));

        assertThat(cause).isInstanceOf(LockoutPolicyNotConfiguredForChannelException.class);
    }

    @Test
    public void shouldReturnLockoutPolicyForChannel() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getChannelId()).willReturn(CHANNEL_ID);
        final LockoutPolicy expectedPolicy = mock(LockoutPolicy.class);
        given(channelPolicies.getPolicyFor(request)).willReturn(expectedPolicy);

        final LockoutPolicy policy = service.getPolicy(request);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    private static ChannelLockoutPolicies buildChannelLockoutPolicies() {
        final ChannelLockoutPolicies policies = mock(ChannelLockoutPolicies.class);
        given(policies.getChannelId()).willReturn(CHANNEL_ID);
        return policies;
    }

}
