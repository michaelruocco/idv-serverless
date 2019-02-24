package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.SensitiveAliasFormat;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class DebitCardNumberAlias extends CardNumberAlias {

    public DebitCardNumberAlias(final SensitiveAliasFormat format, final String value) {
        super(AliasType.DEBIT_CARD_NUMBER, format, value);
    }

}
