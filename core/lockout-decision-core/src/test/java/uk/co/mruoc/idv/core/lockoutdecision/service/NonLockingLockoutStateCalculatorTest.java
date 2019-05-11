package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutType;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import static org.assertj.core.api.Assertions.assertThat;

public class NonLockingLockoutStateCalculatorTest {

    private final LockoutStateCalculator calculator = new NonLockingLockoutStateCalculator();

    @Test
    public void shouldAlwaysReturnNotLockedLockoutState() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .build();

        final LockoutState state = calculator.calculateLockoutState(attempts);

        assertThat(state.isLocked()).isFalse();
    }

    @Test
    public void shouldReturnNumberOfAttempts() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .build();

        final LockoutState state = calculator.calculateLockoutState(attempts);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

    @Test
    public void shouldReturnNonLockingLockoutState() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .build();

        final LockoutState state = calculator.calculateLockoutState(attempts);

        assertThat(state.getType()).isEqualTo(LockoutType.NON_LOCKING);
    }

    @Test
    public void shouldReturnNonLockingLockoutType() {
        final String type = calculator.getType();

        assertThat(type).isEqualTo(LockoutType.NON_LOCKING);
    }

}
