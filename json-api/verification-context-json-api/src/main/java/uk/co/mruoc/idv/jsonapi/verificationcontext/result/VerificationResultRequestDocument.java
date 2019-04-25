package uk.co.mruoc.idv.jsonapi.verificationcontext.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.jsonapi.JsonApiDataItem;

@Getter
@NoArgsConstructor(force = true) //required by jackson
public class VerificationResultRequestDocument {

    private static final String TYPE = "verificationResults";

    private final JsonApiDataItem<VerificationMethodResults> data;

    public VerificationResultRequestDocument(final VerificationMethodResults result) {
        this.data = new JsonApiDataItem<>(TYPE, result);
    }

    @JsonIgnore
    public VerificationMethodResults getResults() {
        return data.getAttributes();
    }

}
