package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;

@JsonDeserialize(as = DefaultLockoutStateResponse.class)
public interface LockoutStateResponseMixin {

    @JsonIgnore
    UUID getId();

}
