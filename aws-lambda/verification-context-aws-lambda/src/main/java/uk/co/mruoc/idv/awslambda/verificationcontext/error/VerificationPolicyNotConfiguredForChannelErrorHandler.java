package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService.VerificationPolicyNotConfiguredForChannelException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;
import java.util.Collections;

public class VerificationPolicyNotConfiguredForChannelErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        return new JsonApiErrorDocument(new VerificationPolicyNotConfiguredForChannelErrorItem(e.getMessage()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return Collections.singleton(VerificationPolicyNotConfiguredForChannelException.class);
    }

}
