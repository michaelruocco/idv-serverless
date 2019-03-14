package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.RsaChannelVerificationPolicies.RsaOnlinePurchaseVerificationPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaChannelVerificationPoliciesTest {

    private final ChannelVerificationPolicies policies = new RsaChannelVerificationPolicies();

    @Test
    public void shouldApplyToRsaChannel() {
        assertThat(policies.appliesToChannel(Channel.Ids.RSA)).isTrue();
        assertThat(policies.appliesToChannel("ANY_OTHER_CHANNEL")).isFalse();
    }

    @Test
    public void shouldReturnPolicyForOnlinePurchase() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.ONLINE_PURCHASE);

        assertThat(policy.isPresent()).isTrue();
        final VerificationPolicy policyValue = policy.get();
        assertThat(policyValue).isInstanceOf(RsaOnlinePurchaseVerificationPolicy.class);
        assertThat(policyValue.getEntries()).hasSize(2);
    }

    @Test
    public void policyShouldContainPhysicalPinsentryRespondAsFirstEntryForOnlinePurchase() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.ONLINE_PURCHASE);

        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.get().getEntries());

        final VerificationMethodPolicyEntry firstEntry = entries.get(0);
        assertThat(firstEntry.getName()).isEqualTo(VerificationMethod.Names.PHYSICAL_PINSENTRY);
        assertThat(firstEntry.getMethods()).hasSize(1);
        final VerificationMethodPolicy methodPolicy = firstEntry.getMethods().iterator().next();
        assertThat(methodPolicy).isInstanceOf(PhysicalPinsentryMethodPolicy.class);
        assertThat(methodPolicy.getMethodName()).isEqualTo(VerificationMethod.Names.PHYSICAL_PINSENTRY);
        assertThat(methodPolicy.getDuration()).isEqualTo(300000);
        final PhysicalPinsentryMethodPolicy pinsentryMethodPolicy = (PhysicalPinsentryMethodPolicy) methodPolicy;
        assertThat(pinsentryMethodPolicy.getFunction()).isEqualTo(PinsentryFunction.RESPOND);
    }

    @Test
    public void policyShouldContainCardCredentialsOtpSmsAsSecondEntryForOnlinePurchase() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.ONLINE_PURCHASE);

        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.get().getEntries());

        final VerificationMethodPolicyEntry secondEntry = entries.get(1);
        assertThat(secondEntry.getName()).isEqualTo("OTP_SMS");
        assertThat(secondEntry.getMethods()).hasSize(2);
    }

    @Test
    public void policyShouldContainCardCredentialsAsFirstOtpSmsMethodForOnlinePurchase() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.ONLINE_PURCHASE);

        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.get().getEntries());

        final VerificationMethodPolicyEntry secondEntry = entries.get(1);
        final List<VerificationMethodPolicy> methodPolicies = new ArrayList<>(secondEntry.getMethods());
        final VerificationMethodPolicy firstMethod = methodPolicies.get(0);
        assertThat(firstMethod.getMethodName()).isEqualTo(VerificationMethod.Names.CARD_CREDENTIALS);
        assertThat(firstMethod.getDuration()).isEqualTo(300000);
    }

    @Test
    public void policyShouldContainOneTimePasscodeSmsAsSecondOtpSmsMethodForOnlinePurchase() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.ONLINE_PURCHASE);

        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.get().getEntries());

        final VerificationMethodPolicyEntry secondEntry = entries.get(1);
        final List<VerificationMethodPolicy> methodPolicies = new ArrayList<>(secondEntry.getMethods());
        final VerificationMethodPolicy secondMethod = methodPolicies.get(1);
        assertThat(secondMethod.getMethodName()).isEqualTo(VerificationMethod.Names.ONE_TIME_PASSCODE_SMS);
        assertThat(secondMethod.getDuration()).isEqualTo(300000);
    }

    @Test
    public void policyShouldContainCorrectOneTimePasscodeSettingsForOnlinePurchase() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.ONLINE_PURCHASE);

        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.get().getEntries());

        final VerificationMethodPolicyEntry secondEntry = entries.get(1);
        final List<VerificationMethodPolicy> methodPolicies = new ArrayList<>(secondEntry.getMethods());
        final VerificationMethodPolicy secondMethod = methodPolicies.get(1);
        assertThat(secondMethod).isInstanceOf(OtpSmsMethodPolicy.class);
        final OtpSmsMethodPolicy otpSmsMethod = (OtpSmsMethodPolicy) secondMethod;
        final Passcode passcode = otpSmsMethod.getPasscode();
        assertThat(passcode.getLength()).isEqualTo(8);
        assertThat(passcode.getDuration()).isEqualTo(150000);
        assertThat(passcode.getAttempts()).isEqualTo(3);
    }

}
