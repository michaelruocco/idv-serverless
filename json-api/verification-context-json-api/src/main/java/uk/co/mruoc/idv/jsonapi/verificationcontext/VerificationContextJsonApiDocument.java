package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

@Getter
@NoArgsConstructor(force = true) // required by jackson
public class VerificationContextJsonApiDocument {

    private static final String TYPE = "verificationContexts";

    private final JsonApiDataItemWithId<VerificationContext> data;

    public VerificationContextJsonApiDocument(final VerificationContext context) {
        this.data = new JsonApiDataItemWithId<>(context.getId(), TYPE, context);
    }

    @JsonIgnore
    public VerificationContext getVerificationContext() {
        return data.getAttributes();
    }

}
