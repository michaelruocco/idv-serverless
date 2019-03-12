package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ActivityVerificationMethodPolicyTest {

    @Test
    public void shouldReturnActivityType() {
        final String activityType = Activity.Types.LOGIN;
        final VerificationMethodPolicyEntry entry = mock(VerificationMethodPolicyEntry.class);

        final ActivityVerificationMethodPolicy policy = new ActivityVerificationMethodPolicy(activityType, entry);

        assertThat(policy.getActivityTypes()).containsExactly(activityType);
    }

    @Test
    public void shouldReturnPolicyEntry() {
        final String activityType = Activity.Types.LOGIN;
        final VerificationMethodPolicyEntry entry = mock(VerificationMethodPolicyEntry.class);

        final ActivityVerificationMethodPolicy policy = new ActivityVerificationMethodPolicy(activityType, entry);

        assertThat(policy.getEntries()).containsExactly(entry);
    }

    @Test
    public void shouldReturnPolicyEntryFromCollection() {
        final String activityType = Activity.Types.LOGIN;
        final Collection<VerificationMethodPolicyEntry> entries = Collections.singleton(mock(VerificationMethodPolicyEntry.class));

        final ActivityVerificationMethodPolicy policy = new ActivityVerificationMethodPolicy(activityType, entries);

        assertThat(policy.getEntries()).containsExactlyElementsOf(entries);
    }

    @Test
    public void shouldReturnActivityTypes() {
        final Collection<String> activityTypes = Arrays.asList(Activity.Types.LOGIN, Activity.Types.ONLINE_PURCHASE);
        final Collection<VerificationMethodPolicyEntry> entries = Collections.singleton(mock(VerificationMethodPolicyEntry.class));

        final ActivityVerificationMethodPolicy policy = new ActivityVerificationMethodPolicy(activityTypes, entries);

        assertThat(policy.getActivityTypes()).containsExactlyElementsOf(activityTypes);
    }

    @Test
    public void shouldReturnPolicyEntries() {
        final Collection<String> activityTypes = Arrays.asList(Activity.Types.LOGIN, Activity.Types.ONLINE_PURCHASE);
        final Collection<VerificationMethodPolicyEntry> entries = Collections.singleton(mock(VerificationMethodPolicyEntry.class));

        final ActivityVerificationMethodPolicy policy = new ActivityVerificationMethodPolicy(activityTypes, entries);

        assertThat(policy.getEntries()).containsExactlyElementsOf(entries);
    }

}
