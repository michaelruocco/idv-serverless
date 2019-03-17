package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface IdvIdAliasMixin {

    @JsonIgnore
    UUID getValueAsUuid();

}
