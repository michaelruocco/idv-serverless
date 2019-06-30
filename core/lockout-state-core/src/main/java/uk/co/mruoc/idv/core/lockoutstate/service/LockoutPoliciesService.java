package uk.co.mruoc.idv.core.lockoutstate.service;

import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;

public interface LockoutPoliciesService {

    LockoutPolicy getPolicy(final LockoutStateRequest request);

    class LockoutPolicyNotConfiguredForChannelException extends RuntimeException {

        public LockoutPolicyNotConfiguredForChannelException(final String channelId) {
            super(channelId);
        }

    }

}
