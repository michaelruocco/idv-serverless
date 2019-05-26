package uk.co.mruoc.idv.core.lockoutdecision.service;

import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;

public interface LockoutPoliciesService {

    ChannelLockoutPolicies getPoliciesForChannel(final String channelId);

    class LockoutPolicyNotConfiguredForChannelException extends RuntimeException {

        public LockoutPolicyNotConfiguredForChannelException(final String channelId) {
            super(channelId);
        }

    }

}
