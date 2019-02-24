package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.AliasFormat;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class CreditCardNumberAlias extends CardNumberAlias {

    public CreditCardNumberAlias(final AliasFormat format, final String value) {
        super(AliasType.CREDIT_CARD_NUMBER, format, value);
    }

}
