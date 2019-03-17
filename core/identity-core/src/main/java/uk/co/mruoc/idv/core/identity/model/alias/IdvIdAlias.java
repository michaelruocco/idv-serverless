package uk.co.mruoc.idv.core.identity.model.alias;

import java.util.UUID;

public class IdvIdAlias extends DefaultAlias {

    private static final AliasType TYPE = new IdvIdAliasType();

    public IdvIdAlias() {
        this(UUID.randomUUID());
    }

    public IdvIdAlias(final String value) {
        this(toUuid(value));
    }

    public IdvIdAlias(final UUID value) {
        super(TYPE, Formats.CLEAR_TEXT, value.toString());
    }

    public UUID getValueAsUuid() {
        return UUID.fromString(getValue());
    }

    private static UUID toUuid(final String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IdvIdNotValidUuidException(id, e);
        }
    }

    public static class IdvIdNotValidUuidException extends RuntimeException {

        public IdvIdNotValidUuidException(final String value, final Throwable cause) {
            super(value, cause);
        }

    }

}
