package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LockedTimeBasedIntervalLockoutStateTest {

    @Test
    public void shouldReturnIsLockedTrue() {
        final LockedTimeBasedIntervalLockoutState state = LockedTimeBasedIntervalLockoutState.builder()
                .build();

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    public void shouldReturnTimeBasedLockoutType() {
        final LockedTimeBasedIntervalLockoutState state = LockedTimeBasedIntervalLockoutState.builder()
                .build();

        assertThat(state.getType()).isEqualTo(LockoutType.TIME_BASED_INTERVAL);
    }

    @Test
    public void shouldReturnAttempts() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final LockedTimeBasedIntervalLockoutState state = LockedTimeBasedIntervalLockoutState.builder()
                .attempts(attempts)
                .build();

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

    @Test
    public void shouldReturnDuration() {
        final Duration duration = Duration.ofMinutes(15);

        final LockedTimeBasedIntervalLockoutState state = LockedTimeBasedIntervalLockoutState.builder()
                .duration(duration)
                .build();

        assertThat(state.getDuration()).contains(duration);
    }

    @Test
    public void shouldReturnDurationInMillis() {
        final Duration duration = Duration.ofMinutes(15);

        final LockedTimeBasedIntervalLockoutState state = LockedTimeBasedIntervalLockoutState.builder()
                .duration(duration)
                .build();

        assertThat(state.getDurationInMillis()).contains(duration.toMillis());
    }

    @Test
    public void shouldReturnLockedUntil() {
        final Instant lockedUntil = Instant.now();

        final LockedTimeBasedIntervalLockoutState state = LockedTimeBasedIntervalLockoutState.builder()
                .lockedUntil(lockedUntil)
                .build();

        assertThat(state.getLockedUntil()).contains(lockedUntil);
    }

}
