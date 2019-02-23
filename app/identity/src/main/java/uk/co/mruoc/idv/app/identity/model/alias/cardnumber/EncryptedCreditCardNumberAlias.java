package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EncryptedCreditCardNumberAlias extends CreditCardNumberAlias {

    public EncryptedCreditCardNumberAlias(final String value) {
        super(SecureAliasFormat.ENCRYPTED, value);
    }

}
