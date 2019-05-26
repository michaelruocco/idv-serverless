package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NonLockingLockoutStateCalculator implements LockoutStateCalculator {

    @Override
    public LockoutState calculateLockoutState(final LockoutStateRequest request) {
        log.info("calculating time based lock from calculator {} with request {}", this, request);
        return NonLockingLockoutState.builder()
                .attempts(request.getAttempts())
                .build();
    }

    @Override
    public String getType() {
        return LockoutType.NON_LOCKING;
    }

}
