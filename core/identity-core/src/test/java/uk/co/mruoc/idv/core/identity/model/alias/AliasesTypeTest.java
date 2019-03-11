package uk.co.mruoc.idv.core.identity.model.alias;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.CardNumberAliasType;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasesTypeTest {

    @Test
    public void shouldReturnTrueForCardNumberAliasTypes() {
        assertThat(AliasType.toAliasType(AliasType.Names.CREDIT_CARD_NUMBER)).isInstanceOf(CardNumberAliasType.class);
        assertThat(AliasType.toAliasType(AliasType.Names.DEBIT_CARD_NUMBER)).isInstanceOf(CardNumberAliasType.class);
        assertThat(AliasType.toAliasType(AliasType.Names.CARD_NUMBER)).isInstanceOf(CardNumberAliasType.class);
    }

    @Test
    public void shouldReturnFalseForInsensitiveAliasTypes() {
        assertThat(AliasType.toAliasType(AliasType.Names.IDV_ID)).isInstanceOf(DefaultAliasType.class);
        assertThat(AliasType.toAliasType(AliasType.Names.UKC_CARDHOLDER_ID)).isInstanceOf(DefaultAliasType.class);
        assertThat(AliasType.toAliasType(AliasType.Names.BUK_CUSTOMER_ID)).isInstanceOf(DefaultAliasType.class);
    }

}
