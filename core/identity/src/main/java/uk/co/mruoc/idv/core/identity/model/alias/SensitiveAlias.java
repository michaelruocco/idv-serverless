package uk.co.mruoc.idv.core.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class SensitiveAlias extends AbstractAlias {

    private final SensitiveAliasFormat format;

    public SensitiveAlias(final AliasType type, final SensitiveAliasFormat format, final String value) {
        super(type, value);
        this.format = format;
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    public SensitiveAliasFormat getFormat() {
        return format;
    }

}
