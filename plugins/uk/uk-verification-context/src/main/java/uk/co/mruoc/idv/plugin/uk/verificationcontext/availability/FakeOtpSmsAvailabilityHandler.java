package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class FakeOtpSmsAvailabilityHandler implements AvailabilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.ONE_TIME_PASSCODE_SMS;

    @Override
    public VerificationMethod loadMethod(final VerificationMethodRequest request) {
        final OtpSmsMethodPolicy methodPolicy = (OtpSmsMethodPolicy) request.getMethodPolicy();
        final Passcode passcode = methodPolicy.getPasscode();
        final Collection<MobileNumber> mobileNumbers = buildMobileNumbers();
        return new OtpSmsVerificationMethod(request.getDuration(), passcode, mobileNumbers);
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
