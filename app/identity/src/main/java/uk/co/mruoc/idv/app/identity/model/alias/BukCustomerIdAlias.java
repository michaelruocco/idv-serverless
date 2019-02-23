package uk.co.mruoc.idv.app.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BukCustomerIdAlias extends AbstractAlias {

    public BukCustomerIdAlias(final String value) {
        super(AliasType.BUK_CUSTOMER_ID, value);
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
