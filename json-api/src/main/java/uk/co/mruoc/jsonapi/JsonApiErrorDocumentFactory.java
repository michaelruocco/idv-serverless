package uk.co.mruoc.jsonapi;

import java.util.Collection;

public interface JsonApiErrorDocumentFactory {

    JsonApiErrorDocument build(final Exception e);

    Collection<Class<?>> getSupportedExceptions();

}
