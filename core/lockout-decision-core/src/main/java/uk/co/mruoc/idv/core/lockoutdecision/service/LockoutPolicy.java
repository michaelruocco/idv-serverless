package uk.co.mruoc.idv.core.lockoutdecision.service;

import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

public interface LockoutPolicy {

    String getType();

    boolean appliesTo(final VerificationAttempt result);

    VerificationAttempts reset(final VerificationAttempts attempts);

    LockoutState calculateLockoutState(final VerificationAttempts attempts);

}
