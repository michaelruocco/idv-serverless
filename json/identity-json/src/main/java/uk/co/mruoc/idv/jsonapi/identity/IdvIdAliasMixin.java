package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public abstract class IdvIdAliasMixin {

    @JsonIgnore
    public abstract UUID getValueAsUuid();

}
