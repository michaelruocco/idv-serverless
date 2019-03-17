package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias.IdvIdNotValidUuidException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;

public class InvalidIdvIdErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        return new JsonApiErrorDocument(new InvalidIdvIdErrorItem(e.getMessage()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(IdvIdNotValidUuidException.class);
    }
    
    public static class InvalidIdvIdErrorItem extends JsonApiErrorItem {

        private static final int STATUS_CODE = 400;
        private static final String TITLE = "Bad Request";
        private static final String DETAIL_TEMPLATE = "IDV ID value is invalid %s it must be a valid UUID";

        public InvalidIdvIdErrorItem(final String value) {
            super(TITLE, toDetail(value), toMeta(value), STATUS_CODE);
        }

        private static String toDetail(final String value) {
            return String.format(DETAIL_TEMPLATE, value);
        }

        private static Map<String, Object> toMeta(final String value) {
            final Map<String, Object> meta = new HashMap<>();
            meta.put("value", value);
            return Collections.unmodifiableMap(meta);
        }

    }

}
