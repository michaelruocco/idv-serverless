package uk.co.mruoc.idv.plugin.uk.identity.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

import static org.assertj.core.api.Assertions.assertThat;

public class UkAliasTypeTest {

    @Test
    public void shouldReturnBukCustomerIdAliasType() {
        final String name = UkAliasType.Names.BUK_CUSTOMER_ID;

        final AliasType type = UkAliasType.toAliasType(name);

        assertThat(type.name()).isEqualTo(name);
        assertThat(type.isCardNumber()).isFalse();
        assertThat(type.isSensitive()).isFalse();
    }

    @Test
    public void shouldReturnUkcCardholderIdAliasType() {
        final String name = UkAliasType.Names.UKC_CARDHOLDER_ID;

        final AliasType type = UkAliasType.toAliasType(name);

        assertThat(type.name()).isEqualTo(name);
        assertThat(type.isCardNumber()).isFalse();
        assertThat(type.isSensitive()).isFalse();
    }

}
