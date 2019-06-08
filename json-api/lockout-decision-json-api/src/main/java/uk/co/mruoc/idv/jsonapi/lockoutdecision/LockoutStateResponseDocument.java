package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

import java.util.UUID;

@Getter
@NoArgsConstructor(force = true)
public class LockoutStateResponseDocument {

    private static final String TYPE = "lockoutStates";

    private final JsonApiDataItemWithId<LockoutStateResponse> data;

    public LockoutStateResponseDocument(final LockoutStateResponse response) {
        this.data = new JsonApiDataItemWithId<>(response.getId(), TYPE, response);
    }

    @JsonIgnore
    public LockoutStateResponse getResponse() {
        return data.getAttributes();
    }

    @JsonIgnore
    public UUID getId() {
        return data.getId();
    }

    @JsonIgnore
    public UUID getIdvId() {
        return getResponse().getIdvId();
    }

}
