package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

public class TokenizedCreditCardNumberAlias extends CreditCardNumberAlias {

    public TokenizedCreditCardNumberAlias(final String value) {
        super(Formats.TOKENIZED, value);
    }

}
