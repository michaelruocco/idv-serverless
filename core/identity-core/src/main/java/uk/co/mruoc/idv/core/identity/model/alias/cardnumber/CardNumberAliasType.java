package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;

public class CardNumberAliasType extends DefaultAliasType {

    public static final String NAME = "CARD_NUMBER";

    public CardNumberAliasType() {
        this(NAME);
    }

    public CardNumberAliasType(final String name) {
        super(name);
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    @Override
    public boolean isCardNumber() {
        return true;
    }

}
