package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

import lombok.ToString;

@ToString(callSuper = true)
public class TokenizedDebitCardNumberAlias extends DebitCardNumberAlias {

    public TokenizedDebitCardNumberAlias(final String value) {
        super(SecureAliasFormat.TOKENIZED, value);
    }

}
