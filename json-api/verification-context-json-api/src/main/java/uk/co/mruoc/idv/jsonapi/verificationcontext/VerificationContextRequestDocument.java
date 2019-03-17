package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.jsonapi.JsonApiDataItem;

@Getter
@NoArgsConstructor(force = true) //required by jackson
public class VerificationContextRequestDocument {

    private static final String TYPE = "verificationContexts";

    private final JsonApiDataItem<VerificationContextRequest> data;

    public VerificationContextRequestDocument(final VerificationContextRequest request) {
        this.data = new JsonApiDataItem<>(TYPE, request);
    }

    @JsonIgnore
    public VerificationContextRequest getRequest() {
        return data.getAttributes();
    }

}
