package uk.co.mruoc.idv.core.lockoutdecision.service;

import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

public interface LockoutStateCalculator {

    LockoutState calculateLockoutState(final VerificationAttempts attempts);

    String getType();

}
