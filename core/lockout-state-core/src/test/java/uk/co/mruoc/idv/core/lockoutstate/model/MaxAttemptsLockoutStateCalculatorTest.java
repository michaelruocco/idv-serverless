package uk.co.mruoc.idv.core.lockoutstate.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MaxAttemptsLockoutStateCalculatorTest {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    private final MaxAttemptsLockoutStateCalculator calculator = new MaxAttemptsLockoutStateCalculator(MAX_NUMBER_OF_ATTEMPTS);

    @Test
    public void shouldReturnMaxNumberOfAttemptsRemainingIfEmptyAttemptsPassed() {
        final VerificationAttempts attempts = VerificationAttempts.builder().build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final MaxAttemptsLockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS);
    }

    @Test
    public void shouldReturnZeroAttemptsRemainingIfMaxNumberOfAttemptsPassed() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt, attempt, attempt))
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final MaxAttemptsLockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(0);
    }

    @Test
    public void shouldReturnNotLockedIfAttemptsRemaining() {
        final VerificationAttempts attempts = VerificationAttempts.builder().build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.isLocked()).isFalse();
    }

    @Test
    public void shouldReturnLockedIfNoAttemptsRemaining() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt, attempt, attempt))
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    public void shouldReturnNumberOfAttempts() {
        final VerificationAttempts attempts = VerificationAttempts.builder().build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();


        final LockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

    @Test
    public void shouldReturnMaxAttemptsLockoutState() {
        final VerificationAttempts attempts = VerificationAttempts.builder().build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = calculator.calculateLockoutState(request);

        assertThat(state.getType()).isEqualTo(LockoutType.MAX_ATTEMPTS);
    }

    @Test
    public void shouldReturnMaxAttemptsLockoutType() {
        final String type = calculator.getType();

        assertThat(type).isEqualTo(LockoutType.MAX_ATTEMPTS);
    }

}
