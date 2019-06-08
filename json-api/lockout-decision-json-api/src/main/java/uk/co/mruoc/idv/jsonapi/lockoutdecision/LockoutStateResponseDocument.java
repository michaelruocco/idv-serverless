package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

@Getter
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

}
