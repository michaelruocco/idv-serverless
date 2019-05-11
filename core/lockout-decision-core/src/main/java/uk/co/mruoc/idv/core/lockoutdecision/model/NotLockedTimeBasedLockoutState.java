package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Getter;

@Getter
public class NotLockedTimeBasedLockoutState extends LockoutState {

    private static final boolean NOT_LOCKED = false;

    public NotLockedTimeBasedLockoutState(final VerificationAttempts attempts, final String type) {
        super(attempts, type, NOT_LOCKED);
    }

}
