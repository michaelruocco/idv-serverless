package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;

public interface VerificationPoliciesService {

    ChannelVerificationPolicies getPoliciesForChannel(final String channelId);

    class VerificationPolicyNotConfiguredForChannelException extends RuntimeException {

        public VerificationPolicyNotConfiguredForChannelException(final String channelId) {
            super(channelId);
        }

    }

}
