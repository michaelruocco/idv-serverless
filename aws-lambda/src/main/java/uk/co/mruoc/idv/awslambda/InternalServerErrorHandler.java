package uk.co.mruoc.idv.awslambda;

import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collection;

import static java.util.Collections.singleton;

public class InternalServerErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        final JsonApiErrorItem error = JsonApiErrorItem.builder()
                .title("Internal Server Error")
                .detail(e.getMessage())
                .build();
        return new JsonApiErrorDocument(error);
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(Exception.class);
    }

}
