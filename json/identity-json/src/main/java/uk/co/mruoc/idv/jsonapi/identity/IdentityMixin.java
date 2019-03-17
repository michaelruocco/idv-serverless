package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.UUID;

public interface IdentityMixin {

    @JsonIgnore
    UUID getIdvIdValue();

    @JsonIgnore
    IdvIdAlias getIdvId();

    @JsonIgnore
    int getAliasCount();

}
