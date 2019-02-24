package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.SensitiveAliasFormat;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.SensitiveAlias;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class CardNumberAlias extends SensitiveAlias {

    public CardNumberAlias(final AliasType type, final SensitiveAliasFormat format, final String value) {
        super(type, format, value);
    }

    @Override
    public boolean isCardNumber() {
        return true;
    }

}
