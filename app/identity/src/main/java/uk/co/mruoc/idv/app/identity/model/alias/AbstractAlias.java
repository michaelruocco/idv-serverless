package uk.co.mruoc.idv.app.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public abstract class AbstractAlias implements Alias {

    private final AliasType type;
    private final String value;

    public AbstractAlias(final AliasType type, final String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public abstract boolean isCardNumber();

    @Override
    public AliasType getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }

}
