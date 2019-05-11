package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaxAttemptsLockoutStateTest {

    @Test
    public void shouldReturnMaxAttemptsType() {
        final MaxAttemptsLockoutState state = MaxAttemptsLockoutState.builder()
                .build();

        assertThat(state.getType()).isEqualTo(LockoutType.MAX_ATTEMPTS);
    }

    @Test
    public void shouldReturnAttemptsRemaining() {
        final int numberOfAttemptsRemaining = 3;

        final MaxAttemptsLockoutState state = MaxAttemptsLockoutState.builder()
                .numberOfAttemptsRemaining(numberOfAttemptsRemaining)
                .build();

        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(numberOfAttemptsRemaining);
    }

    @Test
    public void shouldReturnLockedIfAttemptsRemainingIsZero() {
        final int numberOfAttemptsRemaining = 0;

        final MaxAttemptsLockoutState state = MaxAttemptsLockoutState.builder()
                .numberOfAttemptsRemaining(numberOfAttemptsRemaining)
                .build();

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    public void shouldReturnLockedIfAttemptsRemainingIsLessThanZero() {
        final int numberOfAttemptsRemaining = -1;

        final MaxAttemptsLockoutState state = MaxAttemptsLockoutState.builder()
                .numberOfAttemptsRemaining(numberOfAttemptsRemaining)
                .build();

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    public void shouldReturnNotLockedIfAttemptsRemainingIsGreaterThanZero() {
        final int numberOfAttemptsRemaining = 1;

        final MaxAttemptsLockoutState state = MaxAttemptsLockoutState.builder()
                .numberOfAttemptsRemaining(numberOfAttemptsRemaining)
                .build();

        assertThat(state.isLocked()).isFalse();
    }

}
