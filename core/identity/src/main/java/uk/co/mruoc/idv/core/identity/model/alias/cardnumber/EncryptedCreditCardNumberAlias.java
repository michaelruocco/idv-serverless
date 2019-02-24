package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.SensitiveAliasFormat;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EncryptedCreditCardNumberAlias extends CreditCardNumberAlias {

    public EncryptedCreditCardNumberAlias(final String value) {
        super(SensitiveAliasFormat.ENCRYPTED, value);
    }

}
