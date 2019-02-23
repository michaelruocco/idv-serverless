package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

import org.junit.Test;
import uk.co.mruoc.idv.app.identity.model.alias.Alias;
import uk.co.mruoc.idv.app.identity.model.alias.AliasType;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptedCreditCardNumberAliasTest {

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

    @Test
    public void shouldPrintDetails() {
        assertThat(alias.toString()).isEqualTo("EncryptedCreditCardNumberAlias" +
                "(super=CreditCardNumberAlias" +
                "(super=CardNumberAlias" +
                "(super=AbstractAlias" +
                "(type=CREDIT_CARD_NUMBER, value=NMSADNMj3r2k332lhdasdasaSANdsamdaskh), format=ENCRYPTED)))");
    }

    @Test
    public void testEquals() {
        final Alias alias = new EncryptedCreditCardNumberAlias("ABCDEFGHIJK1234567890");
        final Alias sameAlias = new EncryptedCreditCardNumberAlias("ABCDEFGHIJK1234567890");
        final Alias differentAlias = new EncryptedCreditCardNumberAlias("0987654321KJIHGFEDCBA");

        assertThat(alias.equals(sameAlias)).isTrue();
        assertThat(alias.equals(differentAlias)).isFalse();
    }

}
