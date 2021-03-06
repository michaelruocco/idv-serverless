package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

public class FakeOtpSmsEligibilityHandler extends DelayedEligibilityHandler {

    private static final String DELAY_ENVIRONMENT_VARIABLE_NAME = "OTP_SMS_ELIGIBILITY_DELAY";
    private static final String METHOD_NAME = VerificationMethod.Names.ONE_TIME_PASSCODE_SMS;
    private static final VerificationMethodRequestConverter CONVERTER = new OtpSmsVerificationMethodRequestConverter();

    public FakeOtpSmsEligibilityHandler() {
        super(CONVERTER, METHOD_NAME, DELAY_ENVIRONMENT_VARIABLE_NAME);
    }

}
