package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ChannelVerificationPolicies {

    private final String channelId;
    private final Collection<VerificationPolicy> policies;

    public ChannelVerificationPolicies(final String channelId, final VerificationPolicy... policies) {
        this(channelId, Arrays.asList(policies));
    }

    public ChannelVerificationPolicies(final String channelId, final Collection<VerificationPolicy> policies) {
        this.channelId = channelId;
        this.policies = policies;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public Optional<VerificationPolicy> getPolicyFor(final String activityType) {
        return policies.stream().filter(policy -> policy.appliesTo(activityType)).findFirst();
    }

}
