package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies.LockoutPolicyNotConfiguredForAttemptException;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LockoutPolicyNotConfiguredForAttemptExceptionTest {

    private static final String MESSAGE_FORMAT = "no policy configured for activity %s and alias %s and method %s";
    private static final String ACTIVITY_TYPE_NAME = Activity.Types.LOGIN;
    private static final String ALIAS_TYPE_NAME = AliasType.Names.IDV_ID;
    private static final String METHOD_NAME = VerificationMethod.Names.PHYSICAL_PINSENTRY;

    private final VerificationAttempt attempt = mock(VerificationAttempt.class);

    @Test
    public void shouldReturnActivityType() {
        given(attempt.getActivityType()).willReturn(ACTIVITY_TYPE_NAME);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final LockoutPolicyNotConfiguredForAttemptException exception = new LockoutPolicyNotConfiguredForAttemptException(attempt);

        assertThat(exception.getActivityType()).isEqualTo(ACTIVITY_TYPE_NAME);
    }

    @Test
    public void shouldReturnAliasTypeName() {
        given(attempt.getAliasTypeName()).willReturn(ALIAS_TYPE_NAME);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final LockoutPolicyNotConfiguredForAttemptException exception = new LockoutPolicyNotConfiguredForAttemptException(attempt);

        assertThat(exception.getAliasTypeName()).isEqualTo(ALIAS_TYPE_NAME);
    }

    @Test
    public void shouldReturnMethodName() {
        given(attempt.getMethodName()).willReturn(Optional.of(METHOD_NAME));

        final LockoutPolicyNotConfiguredForAttemptException exception = new LockoutPolicyNotConfiguredForAttemptException(attempt);

        assertThat(exception.getMethodName()).contains(METHOD_NAME);
    }

    @Test
    public void shouldReturnMessage() {
        final String expectedMessage = String.format(MESSAGE_FORMAT, ACTIVITY_TYPE_NAME, ALIAS_TYPE_NAME, METHOD_NAME);
        given(attempt.getActivityType()).willReturn(ACTIVITY_TYPE_NAME);
        given(attempt.getAliasTypeName()).willReturn(ALIAS_TYPE_NAME);
        given(attempt.getMethodName()).willReturn(Optional.of(METHOD_NAME));

        final Throwable cause = new LockoutPolicyNotConfiguredForAttemptException(attempt);

        assertThat(cause.getMessage()).isEqualTo(expectedMessage);
    }

}
