package uk.co.mruoc.idv.core.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class DefaultAliasType implements AliasType {

    private final String name;

    public DefaultAliasType(final String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
