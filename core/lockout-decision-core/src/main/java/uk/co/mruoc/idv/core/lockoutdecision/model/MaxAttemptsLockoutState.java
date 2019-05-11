package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MaxAttemptsLockoutState extends LockoutState {

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
