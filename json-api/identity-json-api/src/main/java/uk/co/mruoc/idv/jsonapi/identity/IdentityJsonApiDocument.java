package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

@Getter
@NoArgsConstructor(force = true) // required by jackson
public class IdentityJsonApiDocument {

    private static final String TYPE = "identities";

    private final JsonApiDataItemWithId<Identity> data;

    public IdentityJsonApiDocument(final Identity identity) {
        this.data = new JsonApiDataItemWithId<>(identity.getIdvId(), TYPE, identity);
    }

    @JsonIgnore
    public Identity getIdentity() {
        return data.getAttributes();
    }

}
