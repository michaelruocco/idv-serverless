package uk.co.mruoc.idv.core.identity.model.alias;

import java.util.UUID;

public class IdvIdAlias extends DefaultAlias {

    private static final AliasType TYPE = new IdvIdAliasType();

    public IdvIdAlias() {
        this(UUID.randomUUID());
    }

    public IdvIdAlias(final String value) {
        this(UUID.fromString(value));
    }

    public IdvIdAlias(final UUID value) {
        super(TYPE, Formats.CLEAR_TEXT, value.toString());
    }

    public UUID getValueAsUuid() {
        return UUID.fromString(getValue());
    }

}
