package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import java.util.Optional;

public interface EligibilityHandler {

    Optional<VerificationMethod> loadMethodIfEligible(final EligibleMethodRequest request);

    boolean isSupported(final EligibleMethodRequest request);

}
