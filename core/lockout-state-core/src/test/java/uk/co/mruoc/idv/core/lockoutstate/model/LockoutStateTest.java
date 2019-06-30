package uk.co.mruoc.idv.core.lockoutstate.model;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LockoutStateTest {

    @Test
    public void shouldReturnEmptyAttemptsRemainingIfTypeIsNotMaxAttempts() {
        final LockoutState state = mock(LockoutState.class);

        final Optional<Integer> attemptsRemaining = LockoutState.extractNumberOfAttemptsRemaining(state);

        assertThat(attemptsRemaining).isEmpty();
    }

    @Test
    public void shouldReturnAttemptsRemainingIfTypeIsMaxAttempts() {
        final int expectedAttemptsRemaining = 1;
        final MaxAttemptsLockoutState state = MaxAttemptsLockoutState.builder()
                .numberOfAttemptsRemaining(expectedAttemptsRemaining)
                .build();

        final Optional<Integer> attemptsRemaining = LockoutState.extractNumberOfAttemptsRemaining(state);

        assertThat(attemptsRemaining).contains(expectedAttemptsRemaining);
    }

    @Test
    public void shouldReturnEmptyDurationIfTypeIsNotTimeBased() {
        final LockoutState state = mock(LockoutState.class);

        final Optional<Long> duration = LockoutState.extractDuration(state);

        assertThat(duration).isEmpty();
    }

    @Test
    public void shouldReturnDurationIfTypeIsTimeBased() {
        final long expectedDuration = Duration.ofMinutes(15).toMillis();
        final TimeBasedLockoutState state = mock(TimeBasedLockoutState.class);
        given(state.isTimeBased()).willReturn(true);
        given(state.getDurationInMillis()).willReturn(Optional.of(expectedDuration));

        final Optional<Long> duration = LockoutState.extractDuration(state);

        assertThat(duration).contains(expectedDuration);
    }

    @Test
    public void shouldReturnEmptyLockedUntilIfTypeIsNotTimeBased() {
        final LockoutState state = mock(LockoutState.class);

        final Optional<Instant> lockedUntil = LockoutState.extractLockedUntil(state);

        assertThat(lockedUntil).isEmpty();
    }

    @Test
    public void shouldReturnLockedUntilIfTypeIsTimeBased() {
        final Instant expectedLockedUntil = Instant.now();
        final TimeBasedLockoutState state = mock(TimeBasedLockoutState.class);
        given(state.isTimeBased()).willReturn(true);
        given(state.getLockedUntil()).willReturn(Optional.of(expectedLockedUntil));

        final Optional<Instant> lockedUntil = LockoutState.extractLockedUntil(state);

        assertThat(lockedUntil).contains(expectedLockedUntil);
    }

}
