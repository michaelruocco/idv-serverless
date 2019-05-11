package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Getter
public class LockedTimeBasedLockoutState extends LockoutState {

    private static final boolean LOCKED = true;

    private final Duration duration;
    private final Instant lockedUntil;

    public LockedTimeBasedLockoutState(final VerificationAttempts attempts,
                                       final String type,
                                       final Duration duration,
                                       final Instant lockedUntil) {
        super(attempts, type, LOCKED);
        this.duration = duration;
        this.lockedUntil = lockedUntil;
    }

}
