package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

public class TokenizedCreditCardNumberAlias extends CreditCardNumberAlias {

    public TokenizedCreditCardNumberAlias(final String value) {
        super(SecureAliasFormat.TOKENIZED, value);
    }

}
