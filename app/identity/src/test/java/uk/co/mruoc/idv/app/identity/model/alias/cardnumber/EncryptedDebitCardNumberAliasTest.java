package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

import org.junit.Test;
import uk.co.mruoc.idv.app.identity.model.alias.AliasType;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptedDebitCardNumberAliasTest {

    private final String VALUE = "NMSADNMj3r2k332lhdasdasaSANdsamdaskh";

    private final CardNumberAlias alias = new EncryptedCreditCardNumberAlias(VALUE);

    @Test
    public void shouldReturnValue() {
        assertThat(alias.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void shouldHaveCorrectType() {
        assertThat(alias.getType()).isEqualTo(AliasType.CREDIT_CARD_NUMBER);
    }

    @Test
    public void isCardNumber() {
        assertThat(alias.isCardNumber()).isTrue();
    }

    @Test
    public void isEncrypted() {
        assertThat(alias.hasFormat(SecureAliasFormat.ENCRYPTED)).isTrue();
    }

}
