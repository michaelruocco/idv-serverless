package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@ToString
public class VerificationPolicy {

    private final Collection<String> activityTypes;
    private final Collection<VerificationSequencePolicy> entries;

    public VerificationPolicy(final String activityType, final VerificationSequencePolicy... entries) {
        this(Collections.singleton(activityType), Arrays.asList(entries));
    }

    public VerificationPolicy(final String activityType, final Collection<VerificationSequencePolicy> entries) {
        this(Collections.singleton(activityType), entries);
    }

    public VerificationPolicy(final Collection<String> activityTypes, final Collection<VerificationSequencePolicy> entries) {
        this.activityTypes = activityTypes;
        this.entries = entries;
    }

    public boolean appliesTo(final String activityType) {
        return activityTypes.contains(activityType);
    }

    public Collection<VerificationSequencePolicy> getSequencePolicies() {
        return entries;
    }

}
