package uk.co.mruoc.idv.core.lockoutstate.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutstate.model.ChannelLockoutPolicies.LockoutPolicyNotConfiguredForRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LockoutPolicyNotConfiguredForRequestExceptionTest {

    private static final String MESSAGE_FORMAT = "no policy configured for activity %s and alias %s";
    private static final String ACTIVITY_TYPE_NAME = "ACTIVITY";
    private static final String ALIAS_TYPE_NAME = AliasType.Names.IDV_ID;

    private final LockoutStateRequest request = mock(LockoutStateRequest.class);

    @Test
    public void shouldReturnActivityType() {
        given(request.getActivityType()).willReturn(ACTIVITY_TYPE_NAME);

        final LockoutPolicyNotConfiguredForRequestException exception = new LockoutPolicyNotConfiguredForRequestException(request);

        assertThat(exception.getActivityType()).isEqualTo(ACTIVITY_TYPE_NAME);
    }

    @Test
    public void shouldReturnAliasTypeName() {
        given(request.getAliasTypeName()).willReturn(ALIAS_TYPE_NAME);

        final LockoutPolicyNotConfiguredForRequestException exception = new LockoutPolicyNotConfiguredForRequestException(request);

        assertThat(exception.getAliasTypeName()).isEqualTo(ALIAS_TYPE_NAME);
    }

    @Test
    public void shouldReturnMessage() {
        final String expectedMessage = String.format(MESSAGE_FORMAT, ACTIVITY_TYPE_NAME, ALIAS_TYPE_NAME);
        given(request.getActivityType()).willReturn(ACTIVITY_TYPE_NAME);
        given(request.getAliasTypeName()).willReturn(ALIAS_TYPE_NAME);

        final Throwable cause = new LockoutPolicyNotConfiguredForRequestException(request);

        assertThat(cause.getMessage()).isEqualTo(expectedMessage);
    }

}
