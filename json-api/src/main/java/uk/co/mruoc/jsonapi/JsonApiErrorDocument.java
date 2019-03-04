package uk.co.mruoc.jsonapi;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class JsonApiErrorDocument {

    private final List<JsonApiErrorItem> errors;

    public JsonApiErrorDocument(final JsonApiErrorItem... error) {
        this(Arrays.asList(error));
    }

    public JsonApiErrorDocument(final List<JsonApiErrorItem> errors) {
        this.errors = errors;
    }

}
