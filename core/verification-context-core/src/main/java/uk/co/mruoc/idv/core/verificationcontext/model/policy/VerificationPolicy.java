package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Getter
public class VerificationPolicy {

    private final Collection<String> activityTypes;
    private final Collection<VerificationMethodPolicyEntry> entries;

    public VerificationPolicy(final String activityType, final VerificationMethodPolicyEntry... entries) {
        this(Collections.singleton(activityType), Arrays.asList(entries));
    }

    public VerificationPolicy(final String activityType, final Collection<VerificationMethodPolicyEntry> entries) {
        this(Collections.singleton(activityType), entries);
    }

    public VerificationPolicy(final Collection<String> activityTypes, final Collection<VerificationMethodPolicyEntry> entries) {
        this.activityTypes = activityTypes;
        this.entries = entries;
    }

}
