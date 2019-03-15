package uk.co.mruoc.idv.plugin.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PhysicalPinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class FakePhysicalPinsentryEligibilityHandler implements EligibilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.PHYSICAL_PINSENTRY;

    @Override
    public Optional<VerificationMethod> loadMethodIfEligible(final EligibleMethodRequest request) {
        final PhysicalPinsentryMethodPolicy method = (PhysicalPinsentryMethodPolicy) request.getMethodPolicy();
        final PinsentryFunction function = method.getFunction();
        final Collection<CardNumber> cardNumbers = buildCardNumbers();
        return Optional.of(new PhysicalPinsentryVerificationMethod(request.getDuration(), function, cardNumbers));
    }

    @Override
    public boolean isSupported(final EligibleMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

    private static Collection<CardNumber> buildCardNumbers() {
        final CardNumber cardNumber = CardNumber.builder()
                .tokenized("3213485412348005")
                .build();
        return Collections.singleton(cardNumber);
    }

}
