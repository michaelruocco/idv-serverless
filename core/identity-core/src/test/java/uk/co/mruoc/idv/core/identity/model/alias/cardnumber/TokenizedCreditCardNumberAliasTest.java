package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizedCreditCardNumberAliasTest {

    private final String VALUE = "4320432489138001";

    private final CardNumberAlias alias = new TokenizedCreditCardNumberAlias(VALUE);

    @Test
    public void shouldReturnValue() {
        assertThat(alias.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void shouldHaveCorrectType() {
        assertThat(alias.getTypeName()).isEqualTo(AliasType.Names.CREDIT_CARD_NUMBER);
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
    public void isTokenized() {
        assertThat(alias.getFormat()).isEqualTo(Alias.Formats.TOKENIZED);
    }

    @Test
    public void shouldPrintDetails() {
        assertThat(alias.toString()).isEqualTo("DefaultAlias" +
                "(type=DefaultAliasType(name=CREDIT_CARD_NUMBER), " +
                "format=TOKENIZED, " +
                "value=4320432489138001)");
    }

    @Test
    public void testEquals() {
        final Alias alias = new TokenizedCreditCardNumberAlias("1234567890123456");
        final Alias sameAlias = new TokenizedCreditCardNumberAlias("1234567890123456");
        final Alias differentAlias = new TokenizedCreditCardNumberAlias("6543210987654321");

        assertThat(alias.equals(sameAlias)).isTrue();
        assertThat(alias.equals(differentAlias)).isFalse();
    }

}
