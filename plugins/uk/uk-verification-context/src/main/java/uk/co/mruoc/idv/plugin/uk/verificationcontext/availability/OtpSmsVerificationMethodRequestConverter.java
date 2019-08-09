package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationStatus;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class OtpSmsVerificationMethodRequestConverter implements VerificationMethodRequestConverter {

    @Override
    public VerificationMethod toAvailableVerificationMethod(final VerificationMethodRequest request) {
        return toVerificationMethod(request, VerificationStatus.AVAILABLE);
    }

    @Override
    public VerificationMethod toUnavailableVerificationMethod(final VerificationMethodRequest request) {
        return toVerificationMethod(request, VerificationStatus.UNAVAILABLE);
    }

    private static VerificationMethod toVerificationMethod(final VerificationMethodRequest request, final VerificationStatus status) {
        final OtpSmsMethodPolicy methodPolicy = (OtpSmsMethodPolicy) request.getMethodPolicy();
        final Passcode passcode = methodPolicy.getPasscode();
        final Collection<MobileNumber> mobileNumbers = buildMobileNumbers();
        return new OtpSmsVerificationMethod(request.getDuration(), passcode, status, mobileNumbers);
    }

    private static Collection<MobileNumber> buildMobileNumbers() {
        final MobileNumber mobileNumber = MobileNumber.builder()
                .id(UUID.fromString("48be7f28-37c2-42b6-956c-a32241310ee6"))
                .masked("********789")
                .build();
        return Collections.singleton(mobileNumber);
    }

}
