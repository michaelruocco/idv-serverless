package uk.co.mruoc.idv.app.identity.model.alias;

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
        assertThat(alias.getType()).isEqualTo(AliasType.BUK_CUSTOMER_ID);
    }

    @Test
    public void isNotCardNumber() {
        assertThat(alias.isCardNumber()).isFalse();
    }

    @Test
    public void shouldPrintDetails() {
        assertThat(alias.toString()).isEqualTo("BukCustomerIdAlias" +
                "(super=AbstractAlias(type=BUK_CUSTOMER_ID, value=87654321))");
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
