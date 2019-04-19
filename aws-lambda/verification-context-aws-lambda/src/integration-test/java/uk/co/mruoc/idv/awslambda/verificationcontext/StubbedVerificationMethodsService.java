package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.model.MethodSequencesRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationMethodsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StubbedVerificationMethodsService implements VerificationMethodsService {

    @Override
    public Collection<VerificationMethodSequence> loadMethodSequences(final MethodSequencesRequest request) {
        final VerificationMethodPolicy policy = extractFirstPolicy(request);
        final VerificationMethod method = new PushNotificationVerificationMethod(policy.getDuration());
        return Collections.singleton(new VerificationMethodSequence(method));
    }

    private static VerificationMethodPolicy extractFirstPolicy(final MethodSequencesRequest request) {
        final List<VerificationSequencePolicy> entries = new ArrayList<>(request.getPolicy().getEntries());
        final List<VerificationMethodPolicy> policies = new ArrayList<>(entries.get(0).getMethods());
        return policies.get(0);
    }

}
