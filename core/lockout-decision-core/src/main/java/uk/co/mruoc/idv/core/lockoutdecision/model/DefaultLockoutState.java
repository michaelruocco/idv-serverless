package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DefaultLockoutState implements LockoutState {

    private final VerificationAttempts attempts;
    private final String lockoutType;
    private final boolean locked;

    @Override
    public UUID getId() {
        return attempts.getLockoutStateId();
    }

    @Override
    public UUID getIdvId() {
        return attempts.getIdvId();
    }

    @Override
    public String getType() {
        return lockoutType;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public int getNumberOfAttempts() {
        return attempts.size();
    }

    @Override
    public boolean isTimeBased() {
        return LockoutType.isTimeBased(lockoutType);
    }

}
