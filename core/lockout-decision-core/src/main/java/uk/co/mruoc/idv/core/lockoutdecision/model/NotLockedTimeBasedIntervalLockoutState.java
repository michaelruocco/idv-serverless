package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Getter;

@Getter
public class NotLockedTimeBasedIntervalLockoutState extends NotLockedTimeBasedLockoutState {

    private static final String TYPE = LockoutType.TIME_BASED_INTERVAL;

    public NotLockedTimeBasedIntervalLockoutState(final VerificationAttempts attempts) {
        super(attempts, TYPE);
    }

}
