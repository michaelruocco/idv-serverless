package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockedTimeBasedIntervalLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutType;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedInterval;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedIntervals;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.service.TimeService;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class TimeBasedLockoutStateCalculatorTest {

    private final TimeService timeService = mock(TimeService.class);
    private final TimeBasedIntervals intervals = mock(TimeBasedIntervals.class);

    private final LockoutStateCalculator calculator = new TimeBasedLockoutStateCalculator(timeService, intervals);

    @Test
    public void shouldReturnTimeBasedIntervalLockoutType() {
        final int size = 1;
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(attempts.size()).willReturn(size);
        given(intervals.getInternalFor(size)).willReturn(Optional.empty());

        final LockoutState state = calculator.calculateLockoutState(attempts);

        assertThat(state.getType()).isEqualTo(LockoutType.TIME_BASED_INTERVAL);
    }

    @Test
    public void shouldReturnLockoutStateWithAttempts() {
        final int size = 3;
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(attempts.size()).willReturn(size);
        given(intervals.getInternalFor(size)).willReturn(Optional.empty());

        final LockoutState state = calculator.calculateLockoutState(attempts);

        assertThat(state.getNumberOfAttempts()).isEqualTo(size);
    }

    @Test
    public void shouldReturnNotLockedStateIfNoIntervalFound() {
        final int size = 2;
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(attempts.size()).willReturn(size);
        given(intervals.getInternalFor(size)).willReturn(Optional.empty());

        final LockoutState state = calculator.calculateLockoutState(attempts);

        assertThat(state.isLocked()).isFalse();
    }

    @Test
    public void shouldReturnNotLockedStateIfIntervalFoundButLockedUntilIsInThePast() {
        final Duration lockDuration = Duration.ofMinutes(15);
        final TimeBasedInterval interval = mock(TimeBasedInterval.class);
        given(interval.getDuration()).willReturn(lockDuration);

        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(attempts.getMostRecentTimestamp()).willReturn(now.minus(Duration.ofMinutes(16)));
        given(attempts.size()).willReturn(2);
        given(intervals.getInternalFor(attempts.size())).willReturn(Optional.of(interval));

        final LockoutState state = calculator.calculateLockoutState(attempts);

        assertThat(state.isLocked()).isFalse();
    }

    @Test
    public void shouldReturnLockedStateIfIntervalFoundAndLockedUntilIsInTheFuture() {
        final Duration lockDuration = Duration.ofMinutes(15);
        final TimeBasedInterval interval = mock(TimeBasedInterval.class);
        given(interval.getDuration()).willReturn(lockDuration);

        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(attempts.getMostRecentTimestamp()).willReturn(now.minus(Duration.ofMinutes(2)));
        given(attempts.size()).willReturn(2);
        given(intervals.getInternalFor(attempts.size())).willReturn(Optional.of(interval));

        final LockoutState state = calculator.calculateLockoutState(attempts);

        assertThat(state.isLocked()).isTrue();
        assertThat(state).isInstanceOf(LockedTimeBasedIntervalLockoutState.class);
        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
        final LockedTimeBasedIntervalLockoutState lockedState = (LockedTimeBasedIntervalLockoutState) state;
        assertThat(lockedState.getDuration()).isEqualTo(lockDuration);
    }

    @Test
    public void shouldReturnLockoutTypeFromIntervals() {
        final String expectedLockoutType = LockoutType.TIME_BASED_RECURRING;
        given(intervals.getType()).willReturn(expectedLockoutType);

        final String type = calculator.getType();

        assertThat(type).isEqualTo(expectedLockoutType);
    }

}
