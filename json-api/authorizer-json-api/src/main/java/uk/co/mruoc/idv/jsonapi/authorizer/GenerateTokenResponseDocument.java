package uk.co.mruoc.idv.jsonapi.authorizer;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public UUID getId() {
        return data.getId();
    }

    @JsonIgnore
    public TokenResponse getTokenResponse() {
        return data.getAttributes();
    }

    @JsonIgnore
    public String getToken() {
        return data.getAttributes().getToken();
    }

}
