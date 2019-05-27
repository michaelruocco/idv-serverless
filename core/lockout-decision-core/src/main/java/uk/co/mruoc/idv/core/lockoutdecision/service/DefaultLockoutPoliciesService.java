package uk.co.mruoc.idv.core.lockoutdecision.service;

import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultLockoutPoliciesService implements LockoutPoliciesService {

    private final Map<String, ChannelLockoutPolicies> channelPolicies = new HashMap<>();

    public DefaultLockoutPoliciesService(final Collection<ChannelLockoutPolicies> policies) {
        policies.forEach(this::add);
    }

    @Override
    public ChannelLockoutPolicies getPoliciesForChannel(final String channelId) {
        if (channelPolicies.containsKey(channelId)) {
            return channelPolicies.get(channelId);
        }
        throw new LockoutPolicyNotConfiguredForChannelException(channelId);
    }

    private void add(final ChannelLockoutPolicies policies) {
        channelPolicies.put(policies.getChannelId(), policies);
    }

}
