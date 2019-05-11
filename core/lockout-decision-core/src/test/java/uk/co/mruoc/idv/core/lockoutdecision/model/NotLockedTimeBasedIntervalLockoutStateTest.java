package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class NotLockedTimeBasedIntervalLockoutStateTest {

    private final VerificationAttempts attempts = mock(VerificationAttempts.class);

    private final NotLockedTimeBasedIntervalLockoutState state = new NotLockedTimeBasedIntervalLockoutState(attempts);

    @Test
    public void shouldReturnIsLockedFalse() {
        assertThat(state.isLocked()).isFalse();
    }

    @Test
    public void shouldReturnTimeBasedLockoutType() {
        assertThat(state.getType()).isEqualTo(LockoutType.TIME_BASED_INTERVAL);
    }

    @Test
    public void shouldReturnAttempts() {
        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

}
