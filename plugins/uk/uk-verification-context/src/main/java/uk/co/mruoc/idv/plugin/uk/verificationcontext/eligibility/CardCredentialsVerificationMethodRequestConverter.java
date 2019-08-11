package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import static uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod.INELIGIBLE;

public class CardCredentialsVerificationMethodRequestConverter implements VerificationMethodRequestConverter {

    @Override
    public VerificationMethod toAvailableVerificationMethod(final VerificationMethodRequest request) {
        return new CardCredentialsVerificationMethod(request.getDuration());
    }

    @Override
    public VerificationMethod toUnavailableVerificationMethod(final VerificationMethodRequest request) {
        return new CardCredentialsVerificationMethod(request.getDuration(), INELIGIBLE);
    }

}
