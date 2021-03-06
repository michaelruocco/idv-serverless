package uk.co.mruoc.idv.core.lockoutstate.model;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

@Getter
public class NonLockingLockoutState extends DefaultLockoutState {

    private static final boolean NOT_LOCKED = false;

    @Builder
    public NonLockingLockoutState(final VerificationAttempts attempts) {
        super(attempts, LockoutType.NON_LOCKING, NOT_LOCKED);
    }

}
