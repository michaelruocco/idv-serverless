package uk.co.mruoc.jsonapi;

import java.util.Collection;

import static java.util.Collections.singleton;

public class InternalServerErrorDocumentFactory implements JsonApiErrorDocumentFactory {

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
                .statusCode(500)
                .code("INTERNAL_SERVER_ERROR")
                .title("Internal server error")
                .detail(e.getMessage())
                .build();
    }

}
