package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

import java.util.UUID;

@Getter
@NoArgsConstructor(force = true) //required by jackson
public class VerificationContextResponseDocument {

    private static final String TYPE = "verificationContexts";

    private final JsonApiDataItemWithId<VerificationContext> data;

    public VerificationContextResponseDocument(final VerificationContext context) {
        this(context.getId(), context);
    }

    public VerificationContextResponseDocument(final UUID id, final VerificationContext context) {
        this.data = new JsonApiDataItemWithId<>(id, TYPE, context);
    }

    @JsonIgnore
    public VerificationContext getContext() {
        return data.getAttributes();
    }

}
