package uk.co.mruoc.idv.jsonapi.verificationcontext;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

@Getter
@NoArgsConstructor(force = true) //required by jackson
public class VerificationContextResponseDocument {

    private static final String TYPE = "verificationContexts";

    private final JsonApiDataItemWithId<VerificationContext> data;

    public VerificationContextResponseDocument(final VerificationContext context) {
        this.data = new JsonApiDataItemWithId<>(context.getId(), TYPE, context);
    }

}
