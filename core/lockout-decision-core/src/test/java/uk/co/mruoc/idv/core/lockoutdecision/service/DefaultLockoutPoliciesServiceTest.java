package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DefaultLockoutPoliciesServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final ChannelLockoutPolicies channelPolicies = new ChannelLockoutPolicies(CHANNEL_ID);

    private final LockoutPoliciesService service = new DefaultLockoutPoliciesService(Collections.singleton(channelPolicies));

    @Test
    public void shouldThrowExceptionIfPoliciesNoConfiguredForChannel() {
        final String anotherChannelId = "ANOTHER_CHANNEL_ID";

        final Throwable cause = catchThrowable(() -> service.getPoliciesForChannel(anotherChannelId));

        assertThat(cause).isInstanceOf(LockoutPoliciesService.LockoutPolicyNotConfiguredForChannelException.class)
                .hasMessage(anotherChannelId);
    }

    @Test
    public void shouldReturnPoliciesForChannelId() {
        final ChannelLockoutPolicies policies = service.getPoliciesForChannel(CHANNEL_ID);

        assertThat(policies).isEqualTo(channelPolicies);
    }

}
