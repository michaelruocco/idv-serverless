package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.service.IdentityService.IdentityNotFoundException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;

public class IdentityNotFoundErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        final IdentityNotFoundException exception = (IdentityNotFoundException) e;
        return new JsonApiErrorDocument(new IdentityNotFoundErrorItem(exception.getMessage(), exception.getAlias()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(IdentityNotFoundException.class);
    }

    public static class IdentityNotFoundErrorItem extends JsonApiErrorItem {

        private static final int STATUS_CODE = 404;
        private static final String TITLE = "Identity not found";
        private static final String DETAIL_TEMPLATE = "Identity not found using alias type %s and %s value %s";

        public IdentityNotFoundErrorItem(final String detail, final Alias alias) {
            super(TITLE, toDetail(alias), toMeta(alias), STATUS_CODE);
        }

        private static String toDetail(final Alias alias) {
            return String.format(DETAIL_TEMPLATE, alias.getTypeName(), alias.getFormat(), alias.getValue());
        }

        private static Map<String, Object> toMeta(final Alias alias) {
            final Map<String, Object> meta = new HashMap<>();
            meta.put("alias", alias);
            return Collections.unmodifiableMap(meta);
        }

    }

}
