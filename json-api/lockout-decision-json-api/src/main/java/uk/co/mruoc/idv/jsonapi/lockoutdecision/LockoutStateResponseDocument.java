package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

@Getter
public class LockoutStateResponseDocument {

    private static final String TYPE = "lockoutStates";

    private final JsonApiDataItemWithId<LockoutState> data;

    public LockoutStateResponseDocument(final LockoutState response) {
        this.data = new JsonApiDataItemWithId<>(response.getId(), TYPE, response);
    }

    @JsonIgnore
    public LockoutState getLockoutState() {
        return data.getAttributes();
    }

}
