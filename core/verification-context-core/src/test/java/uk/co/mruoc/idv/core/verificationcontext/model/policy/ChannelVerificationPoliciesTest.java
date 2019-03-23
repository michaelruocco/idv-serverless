package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies.VerificationPolicyNotConfiguredForActivityException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ChannelVerificationPoliciesTest {

    private static final String CHANNEL_ID = "channelId";
    private static final String ACTIVITY_TYPE = "activityType";

    private static final String EXPECTED_ERROR_MESSAGE = "no policy configured for channel channelId and activity activityType";

    @Test
    public void shouldApplyToChannel() {
        final ChannelVerificationPolicies policies = new ChannelVerificationPolicies(CHANNEL_ID);

        assertThat(policies.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    public void shouldThrowExceptionIfNoPoliciesConfigured() {
        final ChannelVerificationPolicies policies = new ChannelVerificationPolicies(CHANNEL_ID);

        final Throwable thrown = catchThrowable(() -> policies.getPolicyFor(ACTIVITY_TYPE));

        assertThat(thrown)
                .isInstanceOf(VerificationPolicyNotConfiguredForActivityException.class)
                .hasMessage(EXPECTED_ERROR_MESSAGE);
    }

    @Test
    public void shouldThrowExceptionIfNoPoliciesApplyToActivity() {
        final VerificationPolicy policy = mock(VerificationPolicy.class);
        given(policy.appliesTo(ACTIVITY_TYPE)).willReturn(false);
        final ChannelVerificationPolicies policies = new ChannelVerificationPolicies(CHANNEL_ID, policy);

        final Throwable thrown = catchThrowable(() -> policies.getPolicyFor(ACTIVITY_TYPE));

        assertThat(thrown)
                .isInstanceOf(VerificationPolicyNotConfiguredForActivityException.class)
                .hasMessage(EXPECTED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnPolicyIfPolicyAppliesToActivity() {
        final VerificationPolicy policy = mock(VerificationPolicy.class);
        given(policy.appliesTo(ACTIVITY_TYPE)).willReturn(true);

        final ChannelVerificationPolicies policies = new ChannelVerificationPolicies(CHANNEL_ID, policy);

        assertThat(policies.getPolicyFor(ACTIVITY_TYPE)).isEqualTo(policy);
    }

}
