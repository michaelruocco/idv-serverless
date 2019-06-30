package uk.co.mruoc.idv.core.lockoutstate.model;

import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultTimeBasedIntervalsTest {

    private final TimeBasedInterval twelveAttemptsInterval = new TimeBasedInterval(12, Duration.ofDays(1));
    private final TimeBasedInterval threeAttemptsInterval = new TimeBasedInterval(3, Duration.ofMinutes(15));

    private final TimeBasedIntervals intervals = new DefaultTimeBasedIntervals(Arrays.asList(twelveAttemptsInterval, threeAttemptsInterval));

    @Test
    public void shouldReturnEmptyOptionalIfNoIntervalForNumberOfAttempts() {
        final Optional<TimeBasedInterval> interval = intervals.getInternalFor(1);

        assertThat(interval).isEmpty();
    }

    @Test
    public void shouldReturnIntervalMatchingNumberOfAttempts() {
        final Optional<TimeBasedInterval> interval1 = intervals.getInternalFor(twelveAttemptsInterval.getNumberOfAttempts());
        final Optional<TimeBasedInterval> interval2 = intervals.getInternalFor(threeAttemptsInterval.getNumberOfAttempts());

        assertThat(interval1).contains(twelveAttemptsInterval);
        assertThat(interval2).contains(threeAttemptsInterval);
    }

    @Test
    public void shouldReturnIntervalWithMostAttemptsIfAttemptsIsHigherThanMax() {
        final Optional<TimeBasedInterval> interval = intervals.getInternalFor(twelveAttemptsInterval.getNumberOfAttempts() + 1);

        assertThat(interval).contains(twelveAttemptsInterval);
    }

    @Test
    public void shouldReturnTimeBasedIntervalLockoutType() {
        final String type = intervals.getType();

        assertThat(type).isEqualTo(LockoutType.TIME_BASED_INTERVAL);
    }

}
