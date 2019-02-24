package uk.co.mruoc.idv.core.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IdvIdAlias extends AbstractAlias {

    public IdvIdAlias() {
        this(UUID.randomUUID());
    }

    public IdvIdAlias(final UUID value) {
        super(AliasType.IDV_ID, AliasFormat.PLAIN_TEXT, value.toString());
    }

    public UUID getValueAsUuid() {
        return UUID.fromString(getValue());
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
