package uk.co.mruoc.idv.app.identity.model.alias;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BukCustomerIdTest {

    private final String VALUE = "87654321";

    private final Alias alias = new BukCustomerId(VALUE);

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

}
