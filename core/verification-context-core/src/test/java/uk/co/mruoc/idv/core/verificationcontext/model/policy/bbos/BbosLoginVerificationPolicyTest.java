package uk.co.mruoc.idv.core.verificationcontext.model.policy.bbos;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BbosLoginVerificationPolicyTest {

    private final VerificationPolicy policy = new BbosLoginVerificationPolicy();

    @Test
    public void shouldContainOneEntry() {
        final Collection<VerificationMethodPolicyEntry> entries = policy.getEntries();

        assertThat(entries).hasSize(1);
    }

    @Test
    public void shouldContainBbosMobilePinsentryPolicyEntryAsFirstEntry() {
        final List<VerificationMethodPolicyEntry> entries = new ArrayList<>(policy.getEntries());

        assertThat(entries.get(0)).isInstanceOf(BbosMobilePinsentryVerificationPolicyEntry.class);
    }

}
