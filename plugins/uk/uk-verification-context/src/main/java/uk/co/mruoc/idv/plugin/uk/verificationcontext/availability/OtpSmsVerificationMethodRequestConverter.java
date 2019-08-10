package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod.ELIGIBLE;
import static uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod.INELIGIBLE;

public class OtpSmsVerificationMethodRequestConverter implements VerificationMethodRequestConverter {

    @Override
    public VerificationMethod toAvailableVerificationMethod(final VerificationMethodRequest request) {
        return toVerificationMethod(request, ELIGIBLE);
    }

    @Override
    public VerificationMethod toUnavailableVerificationMethod(final VerificationMethodRequest request) {
        return toVerificationMethod(request, INELIGIBLE);
    }

    private static VerificationMethod toVerificationMethod(final VerificationMethodRequest request, final boolean eligible) {
        final OtpSmsMethodPolicy methodPolicy = (OtpSmsMethodPolicy) request.getMethodPolicy();
        final Passcode passcode = methodPolicy.getPasscode();
        final Collection<MobileNumber> mobileNumbers = buildMobileNumbers();
        return new OtpSmsVerificationMethod(request.getDuration(), passcode, eligible, mobileNumbers);
    }

    private static Collection<MobileNumber> buildMobileNumbers() {
        final MobileNumber mobileNumber = MobileNumber.builder()
                .id(UUID.fromString("48be7f28-37c2-42b6-956c-a32241310ee6"))
                .masked("********789")
                .build();
        return Collections.singleton(mobileNumber);
    }

}
