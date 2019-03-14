package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.As3ChannelVerificationPolicies.As3LoginVerificationPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.BbosChannelVerificationPolicies.BbosLoginVerificationPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BbosChannelVerificationPoliciesTest {

    private final ChannelVerificationPolicies policies = new BbosChannelVerificationPolicies();

    @Test
    public void shouldApplyToBbosChannel() {
        assertThat(policies.appliesToChannel(Channel.Ids.BBOS)).isTrue();
        assertThat(policies.appliesToChannel("ANY_OTHER_CHANNEL")).isFalse();
    }

    @Test
    public void shouldReturnPolicyForLogin() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.LOGIN);

        assertThat(policy.isPresent()).isTrue();
        assertThat(policy.get()).isInstanceOf(BbosLoginVerificationPolicy.class);
        assertThat(policy.get().getEntries()).hasSize(1);
    }

    @Test
    public void policyShouldContainMobilePinsentryIdentifyAsFirstEntryForLogin() {
        final Optional<VerificationPolicy> policy = policies.getPolicyFor(Activity.Types.LOGIN);

        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.get().getEntries());

        final VerificationMethodPolicyEntry firstEntry = entries.get(0);
        assertThat(firstEntry.getName()).isEqualTo(VerificationMethod.Names.MOBILE_PINSENTRY);
        assertThat(firstEntry.getMethods()).hasSize(1);
        final VerificationMethodPolicy methodPolicy = firstEntry.getMethods().iterator().next();
        assertThat(methodPolicy).isInstanceOf(MobilePinsentryMethodPolicy.class);
        assertThat(methodPolicy.getMethodName()).isEqualTo(VerificationMethod.Names.MOBILE_PINSENTRY);
        assertThat(methodPolicy.getDuration()).isEqualTo(300000);
        final MobilePinsentryMethodPolicy pinsentryMethodPolicy = (MobilePinsentryMethodPolicy) methodPolicy;
        assertThat(pinsentryMethodPolicy.getFunction()).isEqualTo(PinsentryFunction.IDENTIFY);
    }

}
