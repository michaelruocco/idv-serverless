package uk.co.mruoc.idv.core.lockoutstate.service;

import uk.co.mruoc.idv.core.lockoutstate.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultLockoutPoliciesService implements LockoutPoliciesService {

    private final Map<String, ChannelLockoutPolicies> channelPolicies = new HashMap<>();

    public DefaultLockoutPoliciesService(final Collection<ChannelLockoutPolicies> policies) {
        policies.forEach(this::add);
    }

    @Override
    public LockoutPolicy getPolicy(final LockoutStateRequest request) {
        final String channelId = request.getChannelId();
        if (!channelPolicies.containsKey(channelId)) {
            throw new LockoutPolicyNotConfiguredForChannelException(channelId);
        }
        final ChannelLockoutPolicies policies = channelPolicies.get(channelId);
        return policies.getPolicyFor(request);
    }

    private void add(final ChannelLockoutPolicies policies) {
        channelPolicies.put(policies.getChannelId(), policies);
    }

}
