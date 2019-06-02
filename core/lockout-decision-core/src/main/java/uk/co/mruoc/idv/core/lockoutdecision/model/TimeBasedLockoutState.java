package uk.co.mruoc.idv.core.lockoutdecision.model;

import java.time.Duration;
import java.time.Instant;

public interface TimeBasedLockoutState extends LockoutState {

    Duration getDuration();

    long getDurationInMillis();

    Instant getLockedUntil();

}
