package uk.co.mruoc.idv.core.lockoutdecision.model;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;

public interface LockoutPolicy {

    String getType();

    String getAliasTypeName();

    boolean appliesTo(final LockoutStateRequest attempt);

    VerificationAttempts reset(final Alias alias, final VerificationAttempts attempts);

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

}
