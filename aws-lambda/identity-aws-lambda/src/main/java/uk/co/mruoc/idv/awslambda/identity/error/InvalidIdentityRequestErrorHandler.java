package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityRequestValidator.IdentityRequestInvalidException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;

import static java.util.Collections.singleton;

public class InvalidIdentityRequestErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        return new JsonApiErrorDocument(new InvalidIdentityRequestErrorItem());
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(IdentityRequestInvalidException.class);
    }

}
