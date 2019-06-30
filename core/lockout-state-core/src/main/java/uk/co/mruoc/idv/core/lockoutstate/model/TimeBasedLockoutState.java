package uk.co.mruoc.idv.core.lockoutstate.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public interface TimeBasedLockoutState extends LockoutState {

    Optional<Duration> getDuration();

    Optional<Long> getDurationInMillis();

    Optional<Instant> getLockedUntil();

}
