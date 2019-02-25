package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class JsonApiErrorDocument {

    private final List<JsonApiErrorItem> data;

    public JsonApiErrorDocument(final JsonApiErrorItem... data) {
        this(Arrays.asList(data));
    }

    public JsonApiErrorDocument(final List<JsonApiErrorItem> data) {
        this.data = data;
    }

    @JsonIgnore
    public int getStatusCode() {
        return data.get(0).getStatusCode();
    }

}
