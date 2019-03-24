package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.CardCredentialsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;

public class RsaOtpSmsVerificationPolicyEntry extends VerificationMethodPolicyEntry {

    public RsaOtpSmsVerificationPolicyEntry() {
        super("OTP_SMS", new CardCredentialsMethodPolicy(), new OtpSmsMethodPolicy(new RsaPasscode()));
    }

}
