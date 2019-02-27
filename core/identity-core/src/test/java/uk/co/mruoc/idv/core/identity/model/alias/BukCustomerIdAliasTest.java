package uk.co.mruoc.idv.core.identity.model.alias;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BukCustomerIdAliasTest {

    private final String VALUE = "87654321";

    private final Alias alias = new BukCustomerIdAlias(VALUE);

    @Test
    public void shouldReturnValue() {
        assertThat(alias.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void shouldHaveCorrectType() {
        assertThat(alias.getTypeName()).isEqualTo(BukCustomerIdAliasType.NAME);
    }

    @Test
    public void isNotCardNumber() {
        assertThat(alias.isCardNumber()).isFalse();
    }

    @Test
    public void isNotSensitive() {
        assertThat(alias.isSensitive()).isFalse();
    }

    @Test
    public void shouldPrintDetails() {
        assertThat(alias.toString()).isEqualTo("DefaultAlias" +
                "(type=DefaultAliasType(name=BUK_CUSTOMER_ID), " +
                "format=CLEAR_TEXT, " +
                "value=87654321)");
    }

    @Test
    public void testEquals() {
        final Alias alias = new BukCustomerIdAlias("12345678");
        final Alias sameAlias = new BukCustomerIdAlias("12345678");
        final Alias differentAlias = new BukCustomerIdAlias("12345677");

        assertThat(alias.equals(sameAlias)).isTrue();
        assertThat(alias.equals(differentAlias)).isFalse();
    }

}
