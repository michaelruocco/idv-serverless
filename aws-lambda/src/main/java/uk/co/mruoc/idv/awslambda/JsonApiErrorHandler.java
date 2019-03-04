package uk.co.mruoc.idv.awslambda;

import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;

public interface JsonApiErrorHandler {

    JsonApiErrorDocument handle(final Exception e);

    Collection<Class<?>> getSupportedExceptions();

}
