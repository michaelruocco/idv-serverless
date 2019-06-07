package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutStateRequest;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(as = DefaultLockoutStateRequest.class)
public interface LockoutStateRequestMixin {

    @JsonIgnore
    String getAliasTypeName();

}
