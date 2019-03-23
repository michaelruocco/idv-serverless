package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerificationPoliciesService {

    private final Map<String, ChannelVerificationPolicies> channelPolicies = new HashMap<>();

    public VerificationPoliciesService(final List<ChannelVerificationPolicies> policies) {
        policies.forEach(this::add);
    }

    public ChannelVerificationPolicies getPoliciesForChannel(final String channelId) {
        if (channelPolicies.containsKey(channelId)) {
            return channelPolicies.get(channelId);
        }
        throw new VerificationPolicyNotConfiguredForChannelException(channelId);
    }

    private void add(final ChannelVerificationPolicies policies) {
        channelPolicies.put(policies.getChannelId(), policies);
    }

    public static class VerificationPolicyNotConfiguredForChannelException extends RuntimeException {

        public VerificationPolicyNotConfiguredForChannelException(final String channelId) {
            super(channelId);
        }

    }

}
