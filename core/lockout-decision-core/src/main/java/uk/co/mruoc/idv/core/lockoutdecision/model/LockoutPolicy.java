package uk.co.mruoc.idv.core.lockoutdecision.model;

public interface LockoutPolicy {

    String getType();

    boolean appliesTo(final LockoutStateRequest attempt);

    VerificationAttempts reset(final VerificationAttempts attempts);

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

}
