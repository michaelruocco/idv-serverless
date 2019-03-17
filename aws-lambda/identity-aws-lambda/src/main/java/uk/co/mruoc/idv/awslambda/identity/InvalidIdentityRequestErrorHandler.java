package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.singleton;
import static uk.co.mruoc.idv.awslambda.identity.GetIdentityRequestValidator.*;

public class InvalidIdentityRequestErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        return new JsonApiErrorDocument(new InvalidIdentityRequestErrorItem());
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(IdentityRequestInvalidException.class);
    }

    public static class InvalidIdentityRequestErrorItem extends JsonApiErrorItem {

        private static final int STATUS_CODE = 400;
        private static final String TITLE = "Bad Request";
        private static final String DETAIL = "Either IDV ID or aliasType and aliasValue must be provided";

        public InvalidIdentityRequestErrorItem() {
            super(TITLE, DETAIL, Collections.emptyMap(), STATUS_CODE);
        }

    }

}
