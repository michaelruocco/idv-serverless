package uk.co.mruoc.idv.core.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public abstract class AbstractAlias implements Alias {

    private final AliasType type;
    private final AliasFormat format;
    private final String value;

    public AbstractAlias(final AliasType type, final AliasFormat format, final String value) {
        this.type = type;
        this.format = format;
        this.value = value;
    }

    @Override
    public boolean isSensitive() {
        return format.isSensitive();
    }

    @Override
    public boolean hasFormat(final AliasFormat format) {
        return this.format == format;
    }

    @Override
    public AliasType getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public abstract boolean isCardNumber();

}
