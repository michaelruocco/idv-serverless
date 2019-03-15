package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;

public class EligibleMethodsRequestConverter {

    public EligibleMethodRequest toMethodRequest(final EligibleMethodsRequest request, final VerificationMethodPolicy methodPolicy) {
        return EligibleMethodRequest.builder()
                .channel(request.getChannel())
                .identity(request.getIdentity())
                .inputAlias(request.getInputAlias())
                .methodPolicy(methodPolicy)
                .build();
    }

}
