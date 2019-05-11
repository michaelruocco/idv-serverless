package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class LockoutState {

    private final VerificationAttempts attempts;
    private final String lockoutType;
    private final boolean locked;

    public UUID getId() {
        return attempts.getLockoutStateId();
    }

    public UUID getIdvId() {
        return attempts.getIdvId();
    }

    public String getType() {
        return lockoutType;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getNumberOfAttempts() {
        return attempts.size();
    }

}
