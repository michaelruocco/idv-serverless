package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultVerificationPoliciesService implements VerificationPoliciesService {

    private final Map<String, ChannelVerificationPolicies> channelPolicies = new HashMap<>();

    public DefaultVerificationPoliciesService(final Collection<ChannelVerificationPolicies> policies) {
        policies.forEach(this::add);
    }

    @Override
    public ChannelVerificationPolicies getPoliciesForChannel(final String channelId) {
        if (channelPolicies.containsKey(channelId)) {
            return channelPolicies.get(channelId);
        }
        throw new VerificationPolicyNotConfiguredForChannelException(channelId);
    }

    private void add(final ChannelVerificationPolicies policies) {
        channelPolicies.put(policies.getChannelId(), policies);
    }

}
