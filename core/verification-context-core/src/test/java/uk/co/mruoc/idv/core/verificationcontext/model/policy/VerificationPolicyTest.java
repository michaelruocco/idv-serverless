package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class VerificationPolicyTest {

    @Test
    public void shouldApplyToActivityType() {
        final String activityType = Activity.Types.LOGIN;
        final VerificationSequencePolicy entry = mock(VerificationSequencePolicy.class);

        final VerificationPolicy policy = new VerificationPolicy(activityType, entry);

        assertThat(policy.appliesTo(activityType)).isTrue();
    }

    @Test
    public void shouldNotApplyToOtherActivityType() {
        final VerificationSequencePolicy entry = mock(VerificationSequencePolicy.class);

        final VerificationPolicy policy = new VerificationPolicy(Activity.Types.LOGIN, entry);

        assertThat(policy.appliesTo(Activity.Types.ONLINE_PURCHASE)).isFalse();
    }

    @Test
    public void shouldReturnPolicyEntry() {
        final String activityType = Activity.Types.LOGIN;
        final VerificationSequencePolicy entry = mock(VerificationSequencePolicy.class);

        final VerificationPolicy policy = new VerificationPolicy(activityType, entry);

        assertThat(policy.getSequencePolicies()).containsExactly(entry);
    }

    @Test
    public void shouldReturnPolicyEntryFromCollection() {
        final String activityType = Activity.Types.LOGIN;
        final Collection<VerificationSequencePolicy> entries = Collections.singleton(mock(VerificationSequencePolicy.class));

        final VerificationPolicy policy = new VerificationPolicy(activityType, entries);

        assertThat(policy.getSequencePolicies()).containsExactlyElementsOf(entries);
    }

    @Test
    public void shouldApplyToAllActivityTypes() {
        final Collection<String> activityTypes = Arrays.asList(Activity.Types.LOGIN, Activity.Types.ONLINE_PURCHASE);
        final Collection<VerificationSequencePolicy> entries = Collections.singleton(mock(VerificationSequencePolicy.class));

        final VerificationPolicy policy = new VerificationPolicy(activityTypes, entries);

        assertThat(policy.appliesTo(Activity.Types.LOGIN)).isTrue();
        assertThat(policy.appliesTo(Activity.Types.ONLINE_PURCHASE)).isTrue();
    }

    @Test
    public void shouldReturnPolicyEntries() {
        final Collection<String> activityTypes = Arrays.asList(Activity.Types.LOGIN, Activity.Types.ONLINE_PURCHASE);
        final Collection<VerificationSequencePolicy> entries = Collections.singleton(mock(VerificationSequencePolicy.class));

        final VerificationPolicy policy = new VerificationPolicy(activityTypes, entries);

        assertThat(policy.getSequencePolicies()).containsExactlyElementsOf(entries);
    }

    @Test
    public void shouldPrintValues() {
        final Collection<String> activityTypes = Collections.singleton(Activity.Types.LOGIN);
        final Collection<VerificationSequencePolicy> entries = Collections.emptyList();

        final VerificationPolicy policy = new VerificationPolicy(activityTypes, entries);

        assertThat(policy.toString()).isEqualTo("VerificationPolicy(activityTypes=[LOGIN], entries=[])");
    }

}
