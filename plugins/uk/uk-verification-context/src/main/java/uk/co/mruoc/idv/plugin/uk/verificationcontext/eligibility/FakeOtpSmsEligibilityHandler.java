package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class FakeOtpSmsEligibilityHandler implements EligibilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.ONE_TIME_PASSCODE_SMS;

    @Override
    public Optional<VerificationMethod> loadMethodIfEligible(final VerificationMethodRequest request) {
        final OtpSmsMethodPolicy method = (OtpSmsMethodPolicy) request.getMethodPolicy();
        final Passcode passcode = method.getPasscode();
        final Collection<MobileNumber> mobileNumbers = buildMobileNumbers();
        return Optional.of(new OtpSmsVerificationMethod(request.getDuration(), passcode, mobileNumbers));
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

    private static Collection<MobileNumber> buildMobileNumbers() {
        final MobileNumber mobileNumber = MobileNumber.builder()
                .id(UUID.fromString("48be7f28-37c2-42b6-956c-a32241310ee6"))
                .masked("********789")
                .build();
        return Collections.singleton(mobileNumber);
    }

}
