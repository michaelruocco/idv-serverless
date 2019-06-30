package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

public class RsaCreditCardMaxAttemptsLockoutPolicy extends RsaMaxAttemptsLockoutPolicy {

    public RsaCreditCardMaxAttemptsLockoutPolicy() {
        super(AliasType.Names.CREDIT_CARD_NUMBER);
    }

}
