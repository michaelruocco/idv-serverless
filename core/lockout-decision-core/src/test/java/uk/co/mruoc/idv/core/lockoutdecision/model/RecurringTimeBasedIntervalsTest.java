package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RecurringTimeBasedIntervalsTest {

    private final TimeBasedInterval threeAttemptsInterval = new TimeBasedInterval(3, Duration.ofMinutes(15));

    private final TimeBasedIntervals intervals = new RecurringTimeBasedIntervals(threeAttemptsInterval);

    @Test
    public void shouldReturnEmptyOptionalIfNumberOfAttemptsIsZero() {
        final Optional<TimeBasedInterval> interval = intervals.getInternalFor(0);

        assertThat(interval).isEmpty();
    }

    @Test
    public void shouldReturnEmptyOptionalIfNumberOfAttemptsIsNotDivisableByIntervalNumberOfAttempts() {
        final Optional<TimeBasedInterval> interval = intervals.getInternalFor(1);

        assertThat(interval).isEmpty();
    }

    @Test
    public void shouldReturnIntervalMatchingNumberOfAttemptsIfNumberOfAttemptsIsDivisableByIntervalNumberOfAttempts() {
        final Optional<TimeBasedInterval> interval1 = intervals.getInternalFor(threeAttemptsInterval.getNumberOfAttempts());
        final Optional<TimeBasedInterval> interval2 = intervals.getInternalFor(threeAttemptsInterval.getNumberOfAttempts() * 2);

        assertThat(interval1).contains(threeAttemptsInterval);
        assertThat(interval2).contains(threeAttemptsInterval);
    }

    @Test
    public void shouldReturnTimeBasedRecurringLockoutType() {
        final String type = intervals.getType();

        assertThat(type).isEqualTo(LockoutType.TIME_BASED_RECURRING);
    }

}
