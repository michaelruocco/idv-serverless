package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.jsonapi.JsonApiDataItem;

@Getter
@NoArgsConstructor(force = true) //required by jackson
public class LockoutStateRequestDocument {

    private static final String TYPE = "lockoutStates";

    private final JsonApiDataItem<LockoutStateRequest> data;

    public LockoutStateRequestDocument(final LockoutStateRequest result) {
        this.data = new JsonApiDataItem<>(TYPE, result);
    }

    @JsonIgnore
    public LockoutStateRequest getRequest() {
        return data.getAttributes();
    }

}
