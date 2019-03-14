package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.policy.As3ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.BbosChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.RsaChannelVerificationPolicies;

import java.util.HashMap;
import java.util.Map;

public class VerificationPoliciesService {

    private final Map<String, ChannelVerificationPolicies> channelPolicies = new HashMap<>();

    public VerificationPoliciesService() {
        add(new RsaChannelVerificationPolicies());
        add(new As3ChannelVerificationPolicies());
        add(new BbosChannelVerificationPolicies());
    }

    public ChannelVerificationPolicies getPoliciesForChannel(final String channelId) {
        if (channelPolicies.containsKey(channelId)) {
            return channelPolicies.get(channelId);
        }
        throw new UnrecognisedChannelException(channelId);
    }

    private void add(final ChannelVerificationPolicies policies) {
        channelPolicies.put(policies.getChannelId(), policies);
    }

    public static class UnrecognisedChannelException extends RuntimeException {

        public UnrecognisedChannelException(final String channelId) {
            super(channelId);
        }

    }

}
