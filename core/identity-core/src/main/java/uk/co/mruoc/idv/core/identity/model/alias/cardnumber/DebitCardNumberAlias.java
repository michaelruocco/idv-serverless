package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

public class DebitCardNumberAlias extends CardNumberAlias {

    private static final CardNumberAliasType TYPE = new DebitCardNumberAliasType();

    public DebitCardNumberAlias(final String format, final String value) {
        super(TYPE, format, value);
    }

}
