package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

import org.junit.Test;
import uk.co.mruoc.idv.app.identity.model.alias.AliasType;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizedDebitCardNumberAliasTest  {

    private final String VALUE = "4320432489138001";

    private final CardNumberAlias alias = new TokenizedDebitCardNumberAlias(VALUE);

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
    public void isTokenized() {
        assertThat(alias.hasFormat(SecureAliasFormat.TOKENIZED)).isTrue();
    }

    @Test
    public void shouldPrintDetails() {
        assertThat(alias.toString()).isEqualTo("TokenizedDebitCardNumberAlias" +
                "(super=DebitCardNumberAlias" +
                "(super=CardNumberAlias" +
                "(super=AbstractAlias" +
                "(type=DEBIT_CARD_NUMBER, value=4320432489138001), format=TOKENIZED)))");
    }
}
