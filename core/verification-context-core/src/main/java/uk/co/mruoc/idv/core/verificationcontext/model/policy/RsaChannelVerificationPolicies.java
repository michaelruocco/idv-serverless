package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;

import java.util.Arrays;
import java.util.Collection;

public class RsaChannelVerificationPolicies extends ChannelVerificationPolicies {

    public RsaChannelVerificationPolicies() {
        super(Channel.Ids.RSA, new RsaOnlinePurchaseVerificationPolicy());
    }

    public static class RsaOnlinePurchaseVerificationPolicy extends VerificationPolicy {

        private static final Collection<VerificationMethodPolicyEntry> ENTRIES = Arrays.asList(
                new RsaPhysicalPinsentryPolicyEntry(),
                new RsaOtpSmsVerificationPolicyEntry()
        );

        public RsaOnlinePurchaseVerificationPolicy() {
            super(Activity.Types.ONLINE_PURCHASE, ENTRIES);
        }

    }

    private static class RsaPhysicalPinsentryPolicyEntry extends VerificationMethodPolicyEntry {

        private RsaPhysicalPinsentryPolicyEntry() {
            super(new PhysicalPinsentryMethodPolicy(PinsentryFunction.RESPOND));
        }

    }

    private static class RsaOtpSmsVerificationPolicyEntry extends VerificationMethodPolicyEntry {

        private RsaOtpSmsVerificationPolicyEntry() {
            super("OTP_SMS", new CardCredentialsMethodPolicy(), new OtpSmsMethodPolicy(new RsaPasscode()));
        }

        private static class RsaPasscode extends Passcode {

            private static final int LENGTH = 8;
            private static final int DURATION = 150000;
            private static final int ATTEMPTS = 4;

            private RsaPasscode() {
                super(LENGTH, DURATION, ATTEMPTS);
            }

        }

    }

}
