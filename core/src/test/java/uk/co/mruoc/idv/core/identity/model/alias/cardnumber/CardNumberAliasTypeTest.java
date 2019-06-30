package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

import static org.assertj.core.api.Assertions.assertThat;

public class CardNumberAliasTypeTest {

    private final AliasType aliasType = new CardNumberAliasType();

    @Test
    public void shouldReturnCardNumberTypeName() {
        assertThat(aliasType.name()).isEqualTo(AliasType.Names.CARD_NUMBER);
    }

    @Test
    public void shouldReturnIsCardNumberTrue() {
        assertThat(aliasType.isCardNumber()).isTrue();
    }

    @Test
    public void shouldReturnIsSensitiveTrue() {
        assertThat(aliasType.isSensitive()).isTrue();
    }

}
