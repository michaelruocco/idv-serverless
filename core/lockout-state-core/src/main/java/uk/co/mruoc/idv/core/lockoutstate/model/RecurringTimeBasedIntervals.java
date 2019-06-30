package uk.co.mruoc.idv.core.lockoutstate.model;

import java.util.Optional;

public class RecurringTimeBasedIntervals implements TimeBasedIntervals {

    private final TimeBasedInterval interval;

    public RecurringTimeBasedIntervals(final TimeBasedInterval interval) {
        this.interval = interval;
    }

    @Override
    public Optional<TimeBasedInterval> getInternalFor(final int numberOfAttempts) {
        if (numberOfAttempts == 0) {
            return Optional.empty();
        }

        if (numberOfAttempts % interval.getNumberOfAttempts() != 0) {
            return Optional.empty();
        }

        return Optional.of(interval);
    }

    @Override
    public String getType() {
        return LockoutType.TIME_BASED_RECURRING;
    }

}
