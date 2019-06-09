package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

public class RsaDebitCardMaxAttemptsLockoutPolicy extends RsaMaxAttemptsLockoutPolicy {

    public RsaDebitCardMaxAttemptsLockoutPolicy() {
        super(AliasType.Names.DEBIT_CARD_NUMBER);
    }

}
