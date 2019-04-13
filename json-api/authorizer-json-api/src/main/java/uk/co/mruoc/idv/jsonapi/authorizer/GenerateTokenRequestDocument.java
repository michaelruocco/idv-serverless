package uk.co.mruoc.idv.jsonapi.authorizer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.jsonapi.JsonApiDataItem;

@Getter
@NoArgsConstructor(force = true) // required by jackson
public class GenerateTokenRequestDocument {

    private static final String TYPE = "tokens";

    private final JsonApiDataItem<GenerateTokenRequest> data;

    public GenerateTokenRequestDocument(final GenerateTokenRequest request) {
        this.data = new JsonApiDataItem<>(TYPE, request);
    }

    @JsonIgnore
    public GenerateTokenRequest getRequest() {
        return data.getAttributes();
    }

}
