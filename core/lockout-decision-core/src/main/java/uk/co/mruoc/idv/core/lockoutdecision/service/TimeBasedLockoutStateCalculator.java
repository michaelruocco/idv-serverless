package uk.co.mruoc.idv.core.lockoutdecision.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockedTimeBasedIntervalLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.NotLockedTimeBasedIntervalLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedInterval;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedIntervals;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.service.TimeService;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Slf4j
public class TimeBasedLockoutStateCalculator implements LockoutStateCalculator {

    private final TimeBasedIntervals intervals;
    private final TimeService timeService;

    public TimeBasedLockoutStateCalculator(final TimeService timeService, final TimeBasedIntervals intervals) {
        this.timeService = timeService;
        this.intervals = intervals;
    }

    @Override
    public LockoutState calculateLockoutState(final VerificationAttempts attempts) {
        log.info("calculating time based lock from calculator {} with attempts {} and intervals {}", this, attempts, intervals);

        final Optional<TimeBasedInterval> interval = intervals.getInternalFor(attempts.size());
        if (!interval.isPresent()) {
            log.info("no interval found for number of attempts {}, returning not locked state", attempts.size());
            return new NotLockedTimeBasedIntervalLockoutState(attempts);
        }

        final Instant lockedUntil = calculateLockedUntil(attempts, interval.get());
        if (!isLocked(lockedUntil)) {
            log.info("lock until {} has expired, returning not locked state", lockedUntil);
            return new NotLockedTimeBasedIntervalLockoutState(attempts);
        }

        final Duration duration = interval.get().getDuration();
        log.info("returning time based interval lock with duration {} and locked until {}", duration, lockedUntil);
        return LockedTimeBasedIntervalLockoutState.builder()
                .attempts(attempts)
                .duration(duration)
                .lockedUntil(lockedUntil)
                .build();
    }

    @Override
    public String getType() {
        return intervals.getType();
    }

    private Instant calculateLockedUntil(final VerificationAttempts attempts, final TimeBasedInterval interval) {
        final Instant mostRecentAttemptTimestamp = attempts.getMostRecentTimestamp();
        log.info("most recent attempt timestamp is {}", mostRecentAttemptTimestamp);
        return mostRecentAttemptTimestamp.plus(interval.getDuration());
    }

    private boolean isLocked(final Instant lockedUntil) {
        final Instant now = timeService.now();
        return now.isBefore(lockedUntil);
    }

}
