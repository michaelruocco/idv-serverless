package uk.co.mruoc.idv.core.lockoutstate.model;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

public interface LockoutPolicy {

    String getType();

    String getAliasTypeName();

    boolean appliesTo(final LockoutStateRequest request);

    VerificationAttempts reset(final Alias alias, final VerificationAttempts attempts);

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

}
