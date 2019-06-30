package uk.co.mruoc.idv.jsonapi.lockoutstate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.mruoc.idv.core.lockoutstate.model.DefaultLockoutStateRequest;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(as = DefaultLockoutStateRequest.class)
public interface LockoutStateRequestMixin {

    @JsonIgnore
    UUID getAliasTypeName();

}
