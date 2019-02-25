package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonPropertyOrder({ "type", "attributes" })
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class JsonApiDataItem<T> {

    private final String type;
    private final T attributes;

    public T getAttributes() {
        return attributes;
    }

}
