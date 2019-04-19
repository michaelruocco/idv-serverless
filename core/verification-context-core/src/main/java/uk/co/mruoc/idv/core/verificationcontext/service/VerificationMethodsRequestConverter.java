package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.MethodSequencesRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;

public class VerificationMethodsRequestConverter {

    public VerificationMethodRequest toMethodRequest(final MethodSequencesRequest request, final VerificationMethodPolicy methodPolicy) {
        return VerificationMethodRequest.builder()
                .channel(request.getChannel())
                .identity(request.getIdentity())
                .inputAlias(request.getInputAlias())
                .methodPolicy(methodPolicy)
                .build();
    }

}
