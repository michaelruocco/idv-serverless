package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.lockoutstate.model.DefaultLockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateCalculator;
import uk.co.mruoc.idv.core.lockoutstate.model.MaxAttemptsLockoutStateCalculator;
import uk.co.mruoc.idv.core.lockoutstate.service.VerificationAttemptConverter;

import java.util.Collection;
import java.util.Collections;

public class RsaMaxAttemptsLockoutPolicy extends DefaultLockoutPolicy {

    private static final String ALL = "ALL";
    private static final int MAX_ATTEMPTS = 3;

    public RsaMaxAttemptsLockoutPolicy(final String aliasType) {
        super(buildStateCalculator(), aliasType, activities(), new VerificationAttemptConverter());
    }

    private static LockoutStateCalculator buildStateCalculator() {
        return new MaxAttemptsLockoutStateCalculator(MAX_ATTEMPTS);
    }

    private static Collection<String> activities() {
        return Collections.singleton(ALL);
    }

}
