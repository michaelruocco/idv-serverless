package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TokenizedCreditCardNumberAlias extends CreditCardNumberAlias {

    public TokenizedCreditCardNumberAlias(final String value) {
        super(SecureAliasFormat.TOKENIZED, value);
    }

}