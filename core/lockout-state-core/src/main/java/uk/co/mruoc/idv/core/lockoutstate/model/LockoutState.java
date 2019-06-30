package uk.co.mruoc.idv.core.lockoutstate.model;

import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface LockoutState {

    UUID getId();

    UUID getIdvId();

    String getType();

    boolean isLocked();

    int getNumberOfAttempts();

    VerificationAttempts getAttempts();

    boolean isTimeBased();

    boolean isMaxAttempts();

    static Optional<Integer> extractNumberOfAttemptsRemaining(final LockoutState state) {
        if (state.isMaxAttempts()) {
            final MaxAttemptsLockoutState maxAttemptsState = (MaxAttemptsLockoutState) state;
            return Optional.of(maxAttemptsState.getNumberOfAttemptsRemaining());
        }
        return Optional.empty();
    }

    static Optional<Long> extractDuration(final LockoutState state) {
        if (state.isTimeBased()) {
            final TimeBasedLockoutState timeBasedState = (TimeBasedLockoutState) state;
            return timeBasedState.getDurationInMillis();
        }
        return Optional.empty();
    }

    static Optional<Instant> extractLockedUntil(final LockoutState state) {
        if (state.isTimeBased()) {
            final TimeBasedLockoutState timeBasedState = (TimeBasedLockoutState) state;
            return timeBasedState.getLockedUntil();
        }
        return Optional.empty();
    }

}
