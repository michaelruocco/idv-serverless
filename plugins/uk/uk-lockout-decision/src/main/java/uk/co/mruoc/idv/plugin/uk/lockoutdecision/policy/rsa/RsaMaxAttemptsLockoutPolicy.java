package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateCalculator;
import uk.co.mruoc.idv.core.lockoutdecision.model.MaxAttemptsLockoutStateCalculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class RsaMaxAttemptsLockoutPolicy extends DefaultLockoutPolicy {

    private static final String ALL = "ALL";
    private static final int MAX_ATTEMPTS = 3;

    public RsaMaxAttemptsLockoutPolicy() {
        super(buildStateCalculator(),
                buildAliasTypes(),
                buildActivities());
    }

    private static LockoutStateCalculator buildStateCalculator() {
        return new MaxAttemptsLockoutStateCalculator(MAX_ATTEMPTS);
    }

    private static Collection<String> buildAliasTypes() {
        return Arrays.asList(
                AliasType.Names.CREDIT_CARD_NUMBER,
                AliasType.Names.DEBIT_CARD_NUMBER
        );
    }

    private static Collection<String> buildActivities() {
        return Collections.singleton(ALL);
    }

}
