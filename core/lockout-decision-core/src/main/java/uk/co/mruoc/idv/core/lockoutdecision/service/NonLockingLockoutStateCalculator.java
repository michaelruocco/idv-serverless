package uk.co.mruoc.idv.core.lockoutdecision.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutType;
import uk.co.mruoc.idv.core.lockoutdecision.model.NonLockingLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

@Slf4j
public class NonLockingLockoutStateCalculator implements LockoutStateCalculator {

    @Override
    public LockoutState calculateLockoutState(final VerificationAttempts attempts) {
        log.info("calculating time based lock from calculator {} with attempts {}", this, attempts);
        return NonLockingLockoutState.builder()
                .attempts(attempts)
                .build();
    }

    @Override
    public String getType() {
        return LockoutType.NON_LOCKING;
    }

}
