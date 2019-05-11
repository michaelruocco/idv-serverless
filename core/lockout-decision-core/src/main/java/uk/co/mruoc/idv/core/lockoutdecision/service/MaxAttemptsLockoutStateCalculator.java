package uk.co.mruoc.idv.core.lockoutdecision.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutType;
import uk.co.mruoc.idv.core.lockoutdecision.model.MaxAttemptsLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

@Slf4j
public class MaxAttemptsLockoutStateCalculator implements LockoutStateCalculator {

    private final int maximumNumberOfAttempts;

    public MaxAttemptsLockoutStateCalculator(final int maximumNumberOfAttempts) {
        this.maximumNumberOfAttempts = maximumNumberOfAttempts;
    }

    @Override
    public MaxAttemptsLockoutState calculateLockoutState(final VerificationAttempts attempts) {
        log.info("calculating time based lock from calculator {} with attempts {} and max attempts {}", this, attempts, maximumNumberOfAttempts);
        final int numberOfAttemptsRemaining = maximumNumberOfAttempts - attempts.size();
        return MaxAttemptsLockoutState.builder()
                .attempts(attempts)
                .numberOfAttemptsRemaining(numberOfAttemptsRemaining)
                .build();
    }

    @Override
    public String getType() {
        return LockoutType.MAX_ATTEMPTS;
    }

}
