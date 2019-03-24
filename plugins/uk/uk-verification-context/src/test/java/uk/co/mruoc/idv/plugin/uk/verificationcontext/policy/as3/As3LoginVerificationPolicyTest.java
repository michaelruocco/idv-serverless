package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class As3LoginVerificationPolicyTest {

    private final VerificationPolicy policy = new As3LoginVerificationPolicy();

    @Test
    public void shouldContainTwoEntries() {
        final Collection<VerificationMethodPolicyEntry> entries = policy.getEntries();

        assertThat(entries).hasSize(2);
    }

    @Test
    public void shouldContainAs3PushNotificationPolicyEntryAsFirstEntry() {
        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.getEntries());

        assertThat(entries.get(0)).isInstanceOf(As3PushNotificationPolicyEntry.class);
    }

    @Test
    public void shouldContainAs3PhysicalPinsentryPolicyEntryAsSecondEntry() {
        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.getEntries());

        assertThat(entries.get(1)).isInstanceOf(As3PhysicalPinsentryVerificationPolicyEntry.class);
    }

}
