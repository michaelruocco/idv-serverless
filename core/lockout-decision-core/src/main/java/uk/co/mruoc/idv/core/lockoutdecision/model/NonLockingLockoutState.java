package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NonLockingLockoutState extends LockoutState {

    private static final boolean NOT_LOCKED = false;

    @Builder
    public NonLockingLockoutState(final VerificationAttempts attempts) {
        super(attempts, LockoutType.NON_LOCKING, NOT_LOCKED);
    }

}
