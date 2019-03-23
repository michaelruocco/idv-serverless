package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies.VerificationPolicyNotConfiguredForActivityException;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationPolicyNotConfiguredForActivityExceptionTest {

    private static final String CHANNEL_ID = "channelId";
    private static final String ACTIVITY_TYPE = "activityType";

    private static final String EXPECTED_ERROR_MESSAGE = "no policy configured for channel channelId and activity activityType";

    private final VerificationPolicyNotConfiguredForActivityException exception = new VerificationPolicyNotConfiguredForActivityException(CHANNEL_ID, ACTIVITY_TYPE);

    @Test
    public void shouldReturnExpectedMessage() {
        assertThat(exception.getMessage()).isEqualTo(EXPECTED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnChannelId() {
        assertThat(exception.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    public void shouldReturnActivityType() {
        assertThat(exception.getActivityType()).isEqualTo(ACTIVITY_TYPE);
    }

}
