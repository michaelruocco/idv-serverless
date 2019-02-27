package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizedCardNumberAliasTest {

    private final String VALUE = "4320432489138001";

    private final CardNumberAlias alias = new TokenizedCardNumberAlias(VALUE);

    @Test
    public void shouldReturnValue() {
        assertThat(alias.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void shouldHaveCorrectType() {
        assertThat(alias.getTypeName()).isEqualTo(CardNumberAliasType.NAME);
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
                "(type=DefaultAliasType(name=CARD_NUMBER), " +
                "format=TOKENIZED, " +
                "value=4320432489138001)");
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
