package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.SensitiveAliasFormat;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TokenizedDebitCardNumberAlias extends DebitCardNumberAlias {

    public TokenizedDebitCardNumberAlias(final String value) {
        super(SensitiveAliasFormat.TOKENIZED, value);
    }

}
