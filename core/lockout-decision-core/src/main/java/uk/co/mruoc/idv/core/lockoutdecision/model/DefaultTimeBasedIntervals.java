package uk.co.mruoc.idv.core.lockoutdecision.model;

import java.util.Collection;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

public class DefaultTimeBasedIntervals implements TimeBasedIntervals {

    private final SortedMap<Integer, TimeBasedInterval> intervals = new TreeMap<>();

    public DefaultTimeBasedIntervals(final Collection<TimeBasedInterval> intervals) {
        intervals.forEach(this::add);
    }

    @Override
    public Optional<TimeBasedInterval> getInternalFor(final int numberOfAttempts) {
        final int lastIntervalAttempts = intervals.lastKey();
        if (numberOfAttempts > lastIntervalAttempts) {
            return Optional.of(intervals.get(lastIntervalAttempts));
        }
        return Optional.ofNullable(intervals.get(numberOfAttempts));
    }

    @Override
    public String getType() {
        return LockoutType.TIME_BASED_INTERVAL;
    }

    private void add(final TimeBasedInterval interval) {
        intervals.put(interval.getNumberOfAttempts(), interval);
    }


}
