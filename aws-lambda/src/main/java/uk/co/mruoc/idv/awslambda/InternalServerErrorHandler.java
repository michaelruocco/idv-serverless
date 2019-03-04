package uk.co.mruoc.idv.awslambda;

import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.singleton;

public class InternalServerErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        return new JsonApiErrorDocument(new InternalServerErrorItem(e.getMessage()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(Exception.class);
    }

    public static class InternalServerErrorItem extends JsonApiErrorItem {

        private static final String TITLE = "Internal Server Error";
        private static final int STATUS_CODE = 500;

        public InternalServerErrorItem(final String detail) {
            super(TITLE, detail, Collections.emptyMap(), STATUS_CODE);
        }

    }

}
