package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import java.util.Arrays;
import java.util.Collection;

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

    public VerificationPolicy getPolicyFor(final String activityType) {
        return policies.stream()
                .filter(policy -> policy.appliesTo(activityType))
                .findFirst()
                .orElseThrow(() -> new VerificationPolicyNotConfiguredForActivityException(channelId, activityType));
    }

    public static class VerificationPolicyNotConfiguredForActivityException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "no policy configured for channel %s and activity %s";

        private final String channelId;
        private final String activityType;

        public VerificationPolicyNotConfiguredForActivityException(final String channelId, final String activityType) {
            super(buildMessage(channelId, activityType));
            this.channelId = channelId;
            this.activityType = activityType;
        }

        public String getChannelId() {
            return channelId;
        }

        public String getActivityType() {
            return activityType;
        }

        private static String buildMessage(final String channelId, final String activityType) {
            return String.format(MESSAGE_FORMAT, channelId, activityType);
        }

    }

}
