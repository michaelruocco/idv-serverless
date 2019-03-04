package uk.co.mruoc.idv.awslambda;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ErrorHandlerDelegator {

    private final JsonApiErrorHandler defaultHandler;
    private final Map<Class<?>, JsonApiErrorHandler> handlers = new HashMap<>();

    public ErrorHandlerDelegator(final JsonApiErrorHandler defaultHandler,
                                 final Collection<JsonApiErrorHandler> handlerCollection) {
        this.defaultHandler = defaultHandler;
        handlerCollection.forEach(this::add);
    }

    public JsonApiErrorDocument toDocument(final Exception e) {
        final JsonApiErrorHandler handler = getHandler(e);
        return handler.handle(e);
    }

    private JsonApiErrorHandler getHandler(final Exception e) {
        if (handlers.containsKey(e.getClass())) {
            final JsonApiErrorHandler handler = handlers.get(e.getClass());
            log.info("got exception handler {}" + handler);
            return handler;
        }
        log.info("exception handler not found, returning default handler {}", defaultHandler);
        return defaultHandler;
    }

    private void add(final JsonApiErrorHandler handler) {
        handler.getSupportedExceptions().forEach(exception -> handlers.put(exception, handler));
    }

}
