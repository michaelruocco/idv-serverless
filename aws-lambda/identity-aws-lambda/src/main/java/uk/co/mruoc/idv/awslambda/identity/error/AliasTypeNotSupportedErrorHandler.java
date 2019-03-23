package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderService.AliasTypeNotSupportedException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;
import java.util.Collections;

public class AliasTypeNotSupportedErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        final AliasTypeNotSupportedException exception = (AliasTypeNotSupportedException) e;
        return new JsonApiErrorDocument(new AliasTypeNotSupportedErrorItem(exception.getAliasTypeName(), exception.getChannelId()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return Collections.singleton(AliasTypeNotSupportedException.class);
    }

}
