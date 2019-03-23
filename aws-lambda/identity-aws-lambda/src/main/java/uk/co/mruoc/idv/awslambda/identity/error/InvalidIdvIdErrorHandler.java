package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias.IdvIdNotValidUuidException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;

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

}
