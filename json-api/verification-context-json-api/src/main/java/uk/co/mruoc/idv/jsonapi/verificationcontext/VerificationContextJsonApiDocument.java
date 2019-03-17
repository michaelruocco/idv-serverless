package uk.co.mruoc.idv.jsonapi.verificationcontext;

import lombok.Getter;
import lombok.ToString;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

@Getter
@ToString
public class VerificationContextJsonApiDocument {

    private static final String TYPE = "verificationContexts";

    private final JsonApiDataItemWithId<JsonApiVerificationContext> data;

    public VerificationContextJsonApiDocument(final VerificationContext context) {
        this.data = new JsonApiDataItemWithId<>(context.getId(), TYPE, convert(context));
    }

    private static JsonApiVerificationContext convert(final VerificationContext context) {
        return JsonApiVerificationContext.builder()
                .channel(context.getChannel())
                .providedAlias(context.getProvidedAlias())
                .identity(context.getIdentity())
                .activity(context.getActivity())
                .eligibleMethods(context.getEligibleMethods())
                .created(context.getCreated())
                .expiry(context.getExpiry())
                .build();
    }

}
