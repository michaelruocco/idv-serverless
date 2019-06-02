package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService.LockoutStateIsLockedException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;
import java.util.Collections;

public class LockoutStatelsLockedErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        final LockoutStateIsLockedException exception = (LockoutStateIsLockedException) e;
        return new JsonApiErrorDocument(new LockoutStateIsLockedErrorItem(exception.getLockoutState()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return Collections.singleton(LockoutStateIsLockedException.class);
    }

}
