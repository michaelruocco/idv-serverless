package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutPoliciesService.LockoutPolicyNotConfiguredForChannelException;

import static org.assertj.core.api.Assertions.assertThat;

public class LockoutPolicyNotConfiguredForChannelExceptionTest {

    @Test
    public void shouldReturnMessageAsChannelId() {
        final String channelId = "channelId";

        final Throwable exception = new LockoutPolicyNotConfiguredForChannelException(channelId);

        assertThat(exception.getMessage()).isEqualTo(channelId);
    }

}
