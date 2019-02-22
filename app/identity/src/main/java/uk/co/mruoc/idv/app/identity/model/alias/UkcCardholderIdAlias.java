package uk.co.mruoc.idv.app.identity.model.alias;

public class UkcCardholderIdAlias extends AbstractAlias {

    public UkcCardholderIdAlias(final String value) {
        super(AliasType.UKC_CARDHOLDER_ID, value);
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
