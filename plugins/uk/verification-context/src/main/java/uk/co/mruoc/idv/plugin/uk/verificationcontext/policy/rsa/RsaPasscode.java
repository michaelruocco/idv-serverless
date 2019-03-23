package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;

public class RsaPasscode extends Passcode {

    private static final int LENGTH = 8;
    private static final int DURATION = 150000;
    private static final int ATTEMPTS = 3;

    public RsaPasscode() {
        super(LENGTH, DURATION, ATTEMPTS);
    }

}