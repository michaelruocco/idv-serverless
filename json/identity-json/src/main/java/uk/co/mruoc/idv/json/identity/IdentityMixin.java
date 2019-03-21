package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.UUID;

public interface IdentityMixin {

    @JsonIgnore
    UUID getIdvId();

    @JsonIgnore
    IdvIdAlias getIdvIdAlias();

    @JsonIgnore
    int getAliasCount();

}
