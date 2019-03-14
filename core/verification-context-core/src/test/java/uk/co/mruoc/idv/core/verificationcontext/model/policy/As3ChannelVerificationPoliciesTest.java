package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.As3ChannelVerificationPolicies.As3LoginVerificationPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class As3ChannelVerificationPoliciesTest {

    private final ChannelVerificationPolicies policies = new As3ChannelVerificationPolicies();

    @Test
    public void shouldApplyToAs3Channel() {
        assertThat(policies.appliesToChannel(Channel.Ids.AS3)).isTrue();
        assertThat(policies.appliesToChannel("ANY_OTHER_CHANNEL")).isFalse();
    }

    @Test
    public void shouldReturnPolicyForLogin() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.LOGIN);

        assertThat(policy.isPresent()).isTrue();
        assertThat(policy.get()).isInstanceOf(As3LoginVerificationPolicy.class);
        assertThat(policy.get().getEntries()).hasSize(2);
    }

    @Test
    public void policyShouldContainPushNotificationAsFirstEntryForLogin() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.LOGIN);

        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.get().getEntries());

        final VerificationMethodPolicyEntry firstEntry = entries.get(0);
        assertThat(firstEntry.getName()).isEqualTo(VerificationMethod.Names.PUSH_NOTIFICATION);
        assertThat(firstEntry.getMethods()).hasSize(1);
        final VerificationMethodPolicy methodPolicy = firstEntry.getMethods().iterator().next();
        assertThat(methodPolicy.getMethodName()).isEqualTo(VerificationMethod.Names.PUSH_NOTIFICATION);
        assertThat(methodPolicy.getDuration()).isEqualTo(300000);
    }

    @Test
    public void policyShouldContainPhysicalPinsentryIdentifyAsSecondEntryForLogin() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.LOGIN);

        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.get().getEntries());

        final VerificationMethodPolicyEntry secondEntry = entries.get(1);
        assertThat(secondEntry.getName()).isEqualTo(VerificationMethod.Names.PHYSICAL_PINSENTRY);
        assertThat(secondEntry.getMethods()).hasSize(1);
        final VerificationMethodPolicy methodPolicy = secondEntry.getMethods().iterator().next();
        assertThat(methodPolicy).isInstanceOf(PhysicalPinsentryMethodPolicy.class);
        assertThat(methodPolicy.getMethodName()).isEqualTo(VerificationMethod.Names.PHYSICAL_PINSENTRY);
        assertThat(methodPolicy.getDuration()).isEqualTo(300000);
        final PhysicalPinsentryMethodPolicy pinsentryMethodPolicy = (PhysicalPinsentryMethodPolicy) methodPolicy;
        assertThat(pinsentryMethodPolicy.getFunction()).isEqualTo(PinsentryFunction.IDENTIFY);
    }

}
