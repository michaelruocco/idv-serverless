package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

import uk.co.mruoc.idv.app.identity.model.alias.AliasType;

public abstract class DebitCardNumberAlias extends CardNumberAlias {

    public DebitCardNumberAlias(final SecureAliasFormat format, final String value) {
        super(AliasType.DEBIT_CARD_NUMBER, format, value);
    }

}
