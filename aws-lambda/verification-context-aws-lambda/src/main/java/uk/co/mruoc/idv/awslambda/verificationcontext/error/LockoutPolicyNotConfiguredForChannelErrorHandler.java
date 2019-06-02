package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutPoliciesService.LockoutPolicyNotConfiguredForChannelException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;
import java.util.Collections;

public class LockoutPolicyNotConfiguredForChannelErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        final LockoutPolicyNotConfiguredForChannelException exception = (LockoutPolicyNotConfiguredForChannelException) e;
        return new JsonApiErrorDocument(new LockoutPolicyNotConfiguredForChannelErrorItem(exception.getMessage()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return Collections.singleton(LockoutPolicyNotConfiguredForChannelException.class);
    }

}
