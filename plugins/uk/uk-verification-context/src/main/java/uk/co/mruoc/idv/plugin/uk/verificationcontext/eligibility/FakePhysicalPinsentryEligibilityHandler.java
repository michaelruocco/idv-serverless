package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PhysicalPinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Collection;
import java.util.Collections;

public class FakePhysicalPinsentryEligibilityHandler implements EligibilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.PHYSICAL_PINSENTRY;

    @Override
    public VerificationMethod loadMethod(final VerificationMethodRequest request) {
        final PhysicalPinsentryMethodPolicy methodPolicy = (PhysicalPinsentryMethodPolicy) request.getMethodPolicy();
        final PinsentryFunction function = methodPolicy.getFunction();
        final Collection<CardNumber> cardNumbers = buildCardNumbers();
        return new PhysicalPinsentryVerificationMethod(request.getDuration(), function, cardNumbers);
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

    private static Collection<CardNumber> buildCardNumbers() {
        final CardNumber cardNumber = CardNumber.builder()
                .tokenized("3213485412348005")
                .masked("************8005")
                .build();
        return Collections.singleton(cardNumber);
    }

}
