package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ChannelVerificationPoliciesTest {

    private static final String CHANNEL_ID = "channelId";
    private static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";

    @Test
    public void shouldApplyToChannel() {
        final ChannelVerificationPolicies policies = new ChannelVerificationPolicies(CHANNEL_ID);

        assertThat(policies.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    public void shouldReturnEmptyOptionalIfNoPolicies() {
        final ChannelVerificationPolicies policies = new ChannelVerificationPolicies(CHANNEL_ID);

        assertThat(policies.getPolicyFor(ACTIVITY_TYPE)).isEmpty();
    }

    @Test
    public void shouldReturnEmptyOptionalIfNoPoliciesApplyToActivity() {
        final VerificationPolicy policy = mock(VerificationPolicy.class);
        given(policy.appliesTo(ACTIVITY_TYPE)).willReturn(false);

        final ChannelVerificationPolicies policies = new ChannelVerificationPolicies(CHANNEL_ID, policy);

        assertThat(policies.getPolicyFor(ACTIVITY_TYPE)).isEmpty();
    }

    @Test
    public void shouldReturnPolicyIfPolicyAppliesToActivity() {
        final VerificationPolicy policy = mock(VerificationPolicy.class);
        given(policy.appliesTo(ACTIVITY_TYPE)).willReturn(true);

        final ChannelVerificationPolicies policies = new ChannelVerificationPolicies(CHANNEL_ID, policy);

        assertThat(policies.getPolicyFor(ACTIVITY_TYPE)).isEqualTo(Optional.of(policy));
    }

}
