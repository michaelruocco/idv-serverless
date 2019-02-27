package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

public class TokenizedDebitCardNumberAlias extends DebitCardNumberAlias {

    public TokenizedDebitCardNumberAlias(final String value) {
        super(Formats.TOKENIZED, value);
    }

}
