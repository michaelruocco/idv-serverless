package uk.co.mruoc.idv.app.identity.model.alias;

import lombok.Getter;
import uk.co.mruoc.idv.app.identity.model.alias.AbstractAlias;
import uk.co.mruoc.idv.app.identity.model.alias.AliasType;

@Getter
public class BukCustomerId extends AbstractAlias {

    public BukCustomerId(final String value) {
        super(AliasType.BUK_CUSTOMER_ID, value);
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
