package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

public interface VerificationMethodRequestConverter {

    VerificationMethod toAvailableVerificationMethod(final VerificationMethodRequest request);

    VerificationMethod toUnavailableVerificationMethod(final VerificationMethodRequest request);

}
