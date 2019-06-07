package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutStateRequest;
import uk.co.mruoc.jsonapi.JsonApiDataItem;

@Getter
@NoArgsConstructor(force = true) //required by jackson
public class ResetLockoutStateRequestDocument {

    private static final String TYPE = "lockoutStates";

    private final JsonApiDataItem<DefaultLockoutStateRequest> data;

    public ResetLockoutStateRequestDocument(final DefaultLockoutStateRequest result) {
        this.data = new JsonApiDataItem<>(TYPE, result);
    }

    @JsonIgnore
    public DefaultLockoutStateRequest getRequest() {
        return data.getAttributes();
    }

}
