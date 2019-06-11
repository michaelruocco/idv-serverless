package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.lockoutdecision.model.RegisterAttemptsRequest;
import uk.co.mruoc.jsonapi.JsonApiDataItem;

@Getter
@NoArgsConstructor(force = true) //required by jackson
public class RegisterAttemptsRequestDocument {

    private static final String TYPE = "lockoutStates";

    private final JsonApiDataItem<RegisterAttemptsRequest> data;

    public RegisterAttemptsRequestDocument(final RegisterAttemptsRequest result) {
        this.data = new JsonApiDataItem<>(TYPE, result);
    }

    @JsonIgnore
    public RegisterAttemptsRequest getRequest() {
        return data.getAttributes();
    }

}
