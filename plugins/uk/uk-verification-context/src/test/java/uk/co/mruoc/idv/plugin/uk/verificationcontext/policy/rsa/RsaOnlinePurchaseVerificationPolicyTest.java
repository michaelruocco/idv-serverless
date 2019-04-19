package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaOnlinePurchaseVerificationPolicyTest {

    private final VerificationPolicy policy = new RsaOnlinePurchaseVerificationPolicy();

    @Test
    public void shouldContainTwoEntries() {
        final Collection<VerificationSequencePolicy> entries = policy.getSequencePolicies();

        assertThat(entries).hasSize(2);
    }

    @Test
    public void shouldContainRsaPhysicalPinsentryPolicyEntryAsFirstEntry() {
        final List<VerificationSequencePolicy> entries = new ArrayList<>(policy.getSequencePolicies());

        assertThat(entries.get(0)).isInstanceOf(RsaPhysicalPinsentrySequencePolicy.class);
    }

    @Test
    public void shouldContainRsaOtpSmsVerificationPolicyEntryAsSecondEntry() {
        final List<VerificationSequencePolicy> entries = new ArrayList<>(policy.getSequencePolicies());

        assertThat(entries.get(1)).isInstanceOf(RsaOtpSmsSequencePolicy.class);
    }

}
