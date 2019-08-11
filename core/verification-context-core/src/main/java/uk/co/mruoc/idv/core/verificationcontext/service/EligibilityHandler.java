package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

public interface EligibilityHandler {

    VerificationMethod loadMethod(final VerificationMethodRequest request);

    boolean isSupported(final VerificationMethodRequest request);

}
