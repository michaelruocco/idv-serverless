package uk.co.mruoc.idv.jsonapi.authorizer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;
import uk.co.mruoc.jsonapi.JsonApiDataItemWithId;

import java.util.UUID;

@Getter
@NoArgsConstructor(force = true) // required by jackson
public class GenerateTokenResponseDocument {

    private static final String TYPE = "tokens";

    private final JsonApiDataItemWithId<TokenResponse> data;

    public GenerateTokenResponseDocument(final UUID id, final TokenResponse response) {
        this.data = new JsonApiDataItemWithId<>(id, TYPE, response);
    }

}
