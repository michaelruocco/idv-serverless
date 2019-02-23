package uk.co.mruoc.idv.app.identity.model.alias;

import lombok.ToString;

@ToString(callSuper = true)
public class UkcCardholderIdAlias extends AbstractAlias {

    public UkcCardholderIdAlias(final String value) {
        super(AliasType.UKC_CARDHOLDER_ID, value);
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
