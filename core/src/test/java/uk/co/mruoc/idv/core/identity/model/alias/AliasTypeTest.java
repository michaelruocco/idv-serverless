package uk.co.mruoc.idv.core.identity.model.alias;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasTypeTest {

    @Test
    public void shouldReturnIdvIdAliasType() {
        final String name = AliasType.Names.IDV_ID;

        final AliasType type = AliasType.toAliasType(name);

        assertThat(type.name()).isEqualTo(name);
        assertThat(type.isCardNumber()).isFalse();
        assertThat(type.isSensitive()).isFalse();
    }

    @Test
    public void shouldReturnCardNumberAliasType() {
        final String name = AliasType.Names.CARD_NUMBER;

        final AliasType type = AliasType.toAliasType(name);

        assertThat(type.name()).isEqualTo(name);
        assertThat(type.isCardNumber()).isTrue();
        assertThat(type.isSensitive()).isTrue();
    }

    @Test
    public void shouldReturnCreditCardNumberAliasType() {
        final String name = AliasType.Names.CREDIT_CARD_NUMBER;

        final AliasType type = AliasType.toAliasType(name);

        assertThat(type.name()).isEqualTo(name);
        assertThat(type.isCardNumber()).isTrue();
        assertThat(type.isSensitive()).isTrue();
    }

    @Test
    public void shouldReturnDebitCardNumberAliasType() {
        final String name = AliasType.Names.DEBIT_CARD_NUMBER;

        final AliasType type = AliasType.toAliasType(name);

        assertThat(type.name()).isEqualTo(name);
        assertThat(type.isCardNumber()).isTrue();
        assertThat(type.isSensitive()).isTrue();
    }

    @Test
    public void shouldReturnTrueIfIsIdvAliasType() {
        assertThat(AliasType.isIdvId(AliasType.Names.IDV_ID)).isTrue();
        assertThat(AliasType.isIdvId(AliasType.Names.CARD_NUMBER)).isFalse();
        assertThat(AliasType.isIdvId(AliasType.Names.CREDIT_CARD_NUMBER)).isFalse();
        assertThat(AliasType.isIdvId(AliasType.Names.DEBIT_CARD_NUMBER)).isFalse();
    }

}
