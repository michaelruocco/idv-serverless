package uk.co.mruoc.idv.core.lockoutstate.model;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.time.Duration;
import java.time.Instant;

@Getter
public class LockedTimeBasedIntervalLockoutState extends LockedTimeBasedLockoutState {

    private static final String TYPE = LockoutType.TIME_BASED_INTERVAL;
    private static final boolean LOCKED = true;

    @Builder
    public LockedTimeBasedIntervalLockoutState(final VerificationAttempts attempts,
                                               final Duration duration,
                                               final Instant lockedUntil) {
        super(attempts, TYPE, duration, lockedUntil);
    }

}
