package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.identity.service.AliasLoadFailedException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;
import java.util.Collections;

public class AliasLoadFailedErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(Exception e) {
        final AliasLoadFailedException exception = (AliasLoadFailedException) e;
        return new JsonApiErrorDocument(new AliasLoadFailedErrorItem(exception.getAlias()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return Collections.singleton(AliasLoadFailedException.class);
    }

}
