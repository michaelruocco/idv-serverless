package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibleMethodsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StubbedEligibleMethodsService implements EligibleMethodsService {

    @Override
    public Collection<VerificationMethodSequence> loadEligibleMethodSequences(final EligibleMethodsRequest request) {
        final VerificationMethodPolicy policy = extractFirstPolicy(request);
        final VerificationMethod method = new PushNotificationVerificationMethod(policy.getDuration());
        return Collections.singleton(new VerificationMethodSequence(method));
    }

    private static VerificationMethodPolicy extractFirstPolicy(final EligibleMethodsRequest request) {
        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(request.getPolicy().getEntries());
        final List<VerificationMethodPolicy> policies = new ArrayList<>(entries.get(0).getMethods());
        return policies.get(0);
    }

}
