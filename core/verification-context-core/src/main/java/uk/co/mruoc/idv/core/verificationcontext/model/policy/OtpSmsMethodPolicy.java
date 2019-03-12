package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

@Getter
public class OtpSmsMethodPolicy extends VerificationMethodPolicy {

    private final Passcode passcode;

    public OtpSmsMethodPolicy(final Passcode passcode) {
        this(DEFAULT_DURATION, passcode);
    }

    public OtpSmsMethodPolicy(final int duration, final Passcode passcode) {
        super(VerificationMethod.Names.ONE_TIME_PASSCODE_SMS, duration);
        this.passcode = passcode;
    }

}
