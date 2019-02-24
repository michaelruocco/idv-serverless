package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.AliasFormat;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EncryptedDebitCardNumberAlias extends DebitCardNumberAlias {

    public EncryptedDebitCardNumberAlias(final String value) {
        super(AliasFormat.ENCRYPTED, value);
    }

}
