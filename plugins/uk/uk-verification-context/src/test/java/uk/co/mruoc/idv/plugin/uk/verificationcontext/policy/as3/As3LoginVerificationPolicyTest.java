package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class As3LoginVerificationPolicyTest {

    private final VerificationPolicy policy = new As3LoginVerificationPolicy();

    @Test
    public void shouldContainTwoEntries() {
        final Collection<VerificationSequencePolicy> entries = policy.getSequencePolicies();

        assertThat(entries).hasSize(2);
    }

    @Test
    public void shouldContainAs3PushNotificationPolicyEntryAsFirstEntry() {
        final List<VerificationSequencePolicy> entries = new ArrayList<>(policy.getSequencePolicies());

        assertThat(entries.get(0)).isInstanceOf(As3PushNotificationSequencePolicy.class);
    }

    @Test
    public void shouldContainAs3PhysicalPinsentryPolicyEntryAsSecondEntry() {
        final List<VerificationSequencePolicy> entries = new ArrayList<>(policy.getSequencePolicies());

        assertThat(entries.get(1)).isInstanceOf(As3PhysicalPinsentrySequencePolicy.class);
    }

}
