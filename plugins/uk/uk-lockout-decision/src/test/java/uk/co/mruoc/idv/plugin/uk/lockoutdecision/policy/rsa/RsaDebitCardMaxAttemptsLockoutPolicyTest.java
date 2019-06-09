package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;


import static org.assertj.core.api.Assertions.assertThat;

public class RsaDebitCardMaxAttemptsLockoutPolicyTest {

    private final LockoutPolicy policy = new RsaDebitCardMaxAttemptsLockoutPolicy();

    @Test
    public void shouldReturnDebitCardAliasType() {
        assertThat(policy.getAliasType()).isEqualTo(AliasType.Names.DEBIT_CARD_NUMBER);
    }

}
