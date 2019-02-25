package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@JsonPropertyOrder({ "id", "type" })
@NoArgsConstructor(force = true) // required by jackson
public class JsonApiDataItemWithId<T> extends JsonApiDataItem<T> {

    private final UUID id;

    public JsonApiDataItemWithId(final UUID id, final String type, final T attributes) {
        super(type, attributes);
        this.id = id;
    }

}
