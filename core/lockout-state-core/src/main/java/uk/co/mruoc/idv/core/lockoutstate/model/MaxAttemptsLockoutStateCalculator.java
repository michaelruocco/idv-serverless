package uk.co.mruoc.idv.core.lockoutstate.model;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

@Slf4j
public class MaxAttemptsLockoutStateCalculator implements LockoutStateCalculator {

    private final int maximumNumberOfAttempts;

    public MaxAttemptsLockoutStateCalculator(final int maximumNumberOfAttempts) {
        this.maximumNumberOfAttempts = maximumNumberOfAttempts;
    }

    @Override
    public MaxAttemptsLockoutState calculateLockoutState(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {} and max attempts {}", this, request, maximumNumberOfAttempts);
        final VerificationAttempts attempts = request.getAttempts();
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
