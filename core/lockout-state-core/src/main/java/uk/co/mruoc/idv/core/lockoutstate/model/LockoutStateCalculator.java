package uk.co.mruoc.idv.core.lockoutstate.model;

public interface LockoutStateCalculator {

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

    String getType();

}
