package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

public class FakeCardCredentialsAvailabilityHandler implements AvailabilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.CARD_CREDENTIALS;

    @Override
    public VerificationMethod loadMethod(final VerificationMethodRequest request) {
        return new CardCredentialsVerificationMethod(request.getDuration());
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

}
