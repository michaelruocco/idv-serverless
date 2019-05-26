package uk.co.mruoc.idv.core.lockoutdecision.model;

public interface LockoutPolicy {

    String getType();

    boolean appliesTo(final VerificationAttempt attempt);

    VerificationAttempts reset(final VerificationAttempts attempts);

    LockoutState calculateLockoutState(final LockoutStateRequest request);

}
