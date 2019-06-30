package uk.co.mruoc.idv.core.lockoutstate.model;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

@Getter
public class MaxAttemptsLockoutState extends DefaultLockoutState {

    private static final String TYPE = LockoutType.MAX_ATTEMPTS;

    private final int numberOfAttemptsRemaining;

    @Builder
    public MaxAttemptsLockoutState(final VerificationAttempts attempts,
                                   final int numberOfAttemptsRemaining) {
        super(attempts, TYPE, isLocked(numberOfAttemptsRemaining));
        this.numberOfAttemptsRemaining = numberOfAttemptsRemaining;
    }

    private static boolean isLocked(final int numberOfAttemptsRemaining) {
        return numberOfAttemptsRemaining <= 0;
    }



}
