package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.UUID;

public abstract class IdentityMixin {

    @JsonIgnore
    public abstract UUID getIdvIdValue();

    @JsonIgnore
    public abstract IdvIdAlias getIdvId();

}
