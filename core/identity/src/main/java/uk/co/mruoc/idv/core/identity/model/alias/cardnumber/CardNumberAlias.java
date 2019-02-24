package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.AbstractAlias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class CardNumberAlias extends AbstractAlias {

    private final SecureAliasFormat format;

    public CardNumberAlias(final AliasType type, final SecureAliasFormat format, final String value) {
        super(type, value);
        this.format = format;
    }

    @Override
    public boolean isCardNumber() {
        return true;
    }

    public boolean hasFormat(final SecureAliasFormat format) {
        return this.format == format;
    }

}
