package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;

import java.util.Optional;

public interface EligibilityHandler {

    Optional<VerificationMethod> loadMethodIfEligible(final EligibleMethodsRequest request, final VerificationMethodPolicy policy);

    boolean isSupported(final String channelId, final String methodName);

}
