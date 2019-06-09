package uk.co.mruoc.idv.core.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class DefaultAlias implements Alias {

    private final AliasType type;
    private final String format;
    private final String value;

    public DefaultAlias(final AliasType type, final String format, final String value) {
        this.type = type;
        this.format = format;
        this.value = value;
    }

    @Override
    public String getTypeName() {
        return type.name();
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isCardNumber() {
        return type.isCardNumber();
    }

    @Override
    public boolean isSensitive() {
        return type.isSensitive();
    }

}
