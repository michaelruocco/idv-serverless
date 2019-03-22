package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.identity.service.IdentityService.IdentityNotFoundException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;

import static java.util.Collections.singleton;

public class IdentityNotFoundErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        final IdentityNotFoundException exception = (IdentityNotFoundException) e;
        return new JsonApiErrorDocument(new IdentityNotFoundErrorItem(exception.getAlias()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(IdentityNotFoundException.class);
    }

}
