package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NonLockingLockoutStateCalculatorTest {

    private final LockoutStateCalculator calculator = new NonLockingLockoutStateCalculator();

    @Test
    public void shouldAlwaysReturnNotLockedLockoutState() {
        final VerificationAttempts attempts = VerificationAttempts.builder().build();
        final CalculateLockoutStateRequest request = buildRequest(attempts);

        final LockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.isLocked()).isFalse();
    }

    @Test
    public void shouldReturnNumberOfAttempts() {
        final VerificationAttempts attempts = VerificationAttempts.builder().build();
        final CalculateLockoutStateRequest request = buildRequest(attempts);

        final LockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

    @Test
    public void shouldReturnNonLockingLockoutState() {
        final CalculateLockoutStateRequest request = buildRequest();

        final LockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.getType()).isEqualTo(LockoutType.NON_LOCKING);
    }

    @Test
    public void shouldReturnNonLockingLockoutType() {
        final String type = calculator.getType();

        assertThat(type).isEqualTo(LockoutType.NON_LOCKING);
    }

    private static CalculateLockoutStateRequest buildRequest(final VerificationAttempts attempts) {
        return CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();
    }

    private static CalculateLockoutStateRequest buildRequest() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .build();
        return buildRequest(attempts);
    }

}
