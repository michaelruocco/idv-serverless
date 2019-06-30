package uk.co.mruoc.idv.core.lockoutstate.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NonLockingLockoutStateCalculator implements LockoutStateCalculator {

    @Override
    public LockoutState calculateLockoutState(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {}", this, request);
        return NonLockingLockoutState.builder()
                .attempts(request.getAttempts())
                .build();
    }

    @Override
    public String getType() {
        return LockoutType.NON_LOCKING;
    }

}
