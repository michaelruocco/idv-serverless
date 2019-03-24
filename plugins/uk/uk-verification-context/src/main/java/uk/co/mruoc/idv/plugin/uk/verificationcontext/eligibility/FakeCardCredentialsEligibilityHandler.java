package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Optional;

public class FakeCardCredentialsEligibilityHandler implements EligibilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.CARD_CREDENTIALS;

    @Override
    public Optional<VerificationMethod> loadMethodIfEligible(final EligibleMethodRequest request) {
        return Optional.of(new CardCredentialsVerificationMethod(request.getDuration()));
    }

    @Override
    public boolean isSupported(final EligibleMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

}
