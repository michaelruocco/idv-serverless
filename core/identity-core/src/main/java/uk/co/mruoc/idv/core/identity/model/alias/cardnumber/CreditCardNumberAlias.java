package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

public class CreditCardNumberAlias extends CardNumberAlias {

    private static final CardNumberAliasType TYPE = new CreditCardNumberAliasType();

    public CreditCardNumberAlias(final String format, final String value) {
        super(TYPE, format, value);
    }

}
