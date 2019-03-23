package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies.VerificationPolicyNotConfiguredForActivityException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;
import java.util.Collections;

public class VerificationPolicyNotConfiguredForActivityErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        final VerificationPolicyNotConfiguredForActivityException exception = (VerificationPolicyNotConfiguredForActivityException) e;
        return new JsonApiErrorDocument(new VerificationPolicyNotConfiguredForActivityErrorItem(exception.getChannelId(), exception.getActivityType()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return Collections.singleton(VerificationPolicyNotConfiguredForActivityException.class);
    }

}
