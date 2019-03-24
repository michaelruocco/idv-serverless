package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaOnlinePurchaseVerificationPolicyTest {

    private final VerificationPolicy policy = new RsaOnlinePurchaseVerificationPolicy();

    @Test
    public void shouldContainTwoEntries() {
        final Collection<VerificationMethodPolicyEntry> entries = policy.getEntries();

        assertThat(entries).hasSize(2);
    }

    @Test
    public void shouldContainRsaPhysicalPinsentryPolicyEntryAsFirstEntry() {
        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.getEntries());

        assertThat(entries.get(0)).isInstanceOf(RsaPhysicalPinsentryPolicyEntry.class);
    }

    @Test
    public void shouldContainRsaOtpSmsVerificationPolicyEntryAsSecondEntry() {
        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.getEntries());

        assertThat(entries.get(1)).isInstanceOf(RsaOtpSmsVerificationPolicyEntry.class);
    }

}
