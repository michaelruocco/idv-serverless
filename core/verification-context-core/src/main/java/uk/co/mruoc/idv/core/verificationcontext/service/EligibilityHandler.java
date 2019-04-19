package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import java.util.Optional;

public interface EligibilityHandler {

    Optional<VerificationMethod> loadMethodIfEligible(final VerificationMethodRequest request);

    boolean isSupported(final VerificationMethodRequest request);

}
