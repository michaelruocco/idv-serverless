package uk.co.mruoc.idv.app.identity.model.alias;

import lombok.ToString;

@ToString(callSuper = true)
public class BukCustomerId extends AbstractAlias {

    public BukCustomerId(final String value) {
        super(AliasType.BUK_CUSTOMER_ID, value);
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
