package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;

public class CardNumberAlias extends DefaultAlias {

    public CardNumberAlias(final String format, final String value) {
        this(new CardNumberAliasType(), format, value);
    }

    public CardNumberAlias(final CardNumberAliasType type, final String format, final String value) {
        super(type, format, value);
    }

}
