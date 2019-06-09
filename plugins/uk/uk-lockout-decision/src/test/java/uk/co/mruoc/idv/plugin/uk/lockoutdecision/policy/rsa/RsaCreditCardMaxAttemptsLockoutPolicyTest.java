package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaCreditCardMaxAttemptsLockoutPolicyTest {

    private final LockoutPolicy policy = new RsaCreditCardMaxAttemptsLockoutPolicy();

    @Test
    public void shouldReturnCreditCardAliasType() {
        assertThat(policy.getAliasType()).isEqualTo(AliasType.Names.CREDIT_CARD_NUMBER);
    }

}
