package uk.co.mruoc.idv.jsonapi.verificationcontext.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

import java.util.UUID;

@Getter
public class VerificationResultResponseDocument {

    private static final String TYPE = "verificationResults";

    private final JsonApiDataItemWithId<VerificationMethodResults> data;

    public VerificationResultResponseDocument(final VerificationMethodResults results) {
        this(results.getId(), results);
    }

    public VerificationResultResponseDocument(final UUID id, final VerificationMethodResults results) {
        this.data = new JsonApiDataItemWithId<>(id, TYPE, results);
    }

    @JsonIgnore
    public VerificationMethodResults getResults() {
        return data.getAttributes();
    }

    @JsonIgnore
    public UUID getId() {
        return data.getId();
    }

    @JsonIgnore
    public UUID getContextId() {
        return getResults().getContextId();
    }

}
