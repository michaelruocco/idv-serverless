package uk.co.mruoc.idv.jsonapi.lockoutstate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface LockoutStateResponseMixin {

    @JsonIgnore
    UUID getId();

}
