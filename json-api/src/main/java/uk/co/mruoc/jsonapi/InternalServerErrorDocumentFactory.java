package uk.co.mruoc.jsonapi;

import java.util.Collection;

import static java.util.Collections.singleton;

public class InternalServerErrorDocumentFactory implements JsonApiErrorDocumentFactory {

    private static final int STATUS_CODE = 500;

    @Override
    public JsonApiErrorDocument build(final Exception e) {
        return new JsonApiErrorDocument(toErrorItem(e));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(Exception.class);
    }

    private static JsonApiErrorItem toErrorItem(final Exception e) {
        return JsonApiErrorItem.builder()
                .title("Internal server error")
                .detail(e.getMessage())
                .statusCode(STATUS_CODE)
                .build();
    }

}
