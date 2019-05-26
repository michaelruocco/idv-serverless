package uk.co.mruoc.idv.core.lockoutdecision.model;

public interface LockoutStateCalculator {

    LockoutState calculateLockoutState(final LockoutStateRequest request);

    String getType();

}
