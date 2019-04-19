package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.CardCredentialsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;

public class RsaOtpSmsSequencePolicy extends VerificationSequencePolicy {

    public RsaOtpSmsSequencePolicy() {
        super("OTP_SMS", new CardCredentialsMethodPolicy(), new OtpSmsMethodPolicy(new RsaPasscode()));
    }

}
