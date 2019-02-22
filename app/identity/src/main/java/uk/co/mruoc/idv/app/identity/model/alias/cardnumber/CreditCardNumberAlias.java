package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

import uk.co.mruoc.idv.app.identity.model.alias.AliasType;

public abstract class CreditCardNumberAlias extends CardNumberAlias {

    public CreditCardNumberAlias(final SecureAliasFormat format, final String value) {
        super(AliasType.CREDIT_CARD_NUMBER, format, value);
    }

}
