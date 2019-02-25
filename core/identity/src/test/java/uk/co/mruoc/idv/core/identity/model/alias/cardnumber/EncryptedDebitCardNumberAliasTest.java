package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.SensitiveAliasFormat;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptedDebitCardNumberAliasTest {

    private final String VALUE = "NMSADNMj3r2k332lhdasdasaSANdsamdaskh";

    private final CardNumberAlias alias = new EncryptedDebitCardNumberAlias(VALUE);

    @Test
    public void shouldReturnValue() {
        assertThat(alias.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void shouldHaveCorrectType() {
        assertThat(alias.getType()).isEqualTo(AliasType.DEBIT_CARD_NUMBER);
    }

    @Test
    public void isCardNumber() {
        assertThat(alias.isCardNumber()).isTrue();
    }

    @Test
    public void isSensitive() {
        assertThat(alias.isSensitive()).isTrue();
    }

    @Test
    public void isEncrypted() {
        assertThat(alias.getFormat()).isEqualTo(SensitiveAliasFormat.ENCRYPTED);
    }

    @Test
    public void shouldPrintDetails() {
        assertThat(alias.toString()).isEqualTo("EncryptedDebitCardNumberAlias" +
                "(super=DebitCardNumberAlias" +
                "(super=CardNumberAlias" +
                "(super=SensitiveAlias" +
                "(super=AbstractAlias" +
                "(type=DEBIT_CARD_NUMBER, value=NMSADNMj3r2k332lhdasdasaSANdsamdaskh), format=ENCRYPTED))))");
    }

    @Test
    public void testEquals() {
        final Alias alias = new EncryptedDebitCardNumberAlias("ABCDEFGHIJK1234567890");
        final Alias sameAlias = new EncryptedDebitCardNumberAlias("ABCDEFGHIJK1234567890");
        final Alias differentAlias = new EncryptedDebitCardNumberAlias("0987654321KJIHGFEDCBA");

        assertThat(alias.equals(sameAlias)).isTrue();
        assertThat(alias.equals(differentAlias)).isFalse();
    }

}
