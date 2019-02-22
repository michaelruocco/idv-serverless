package uk.co.mruoc.idv.app.identity.model.alias;

import java.util.UUID;

public class IdvIdAlias extends AbstractAlias {

    public IdvIdAlias() {
        this(UUID.randomUUID());
    }

    public IdvIdAlias(final UUID value) {
        super(AliasType.IDV_ID, value.toString());
    }

    public UUID getValueAsUuid() {
        return UUID.fromString(getValue());
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
