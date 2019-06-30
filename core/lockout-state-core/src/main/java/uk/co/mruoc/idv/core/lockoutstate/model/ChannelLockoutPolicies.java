package uk.co.mruoc.idv.core.lockoutstate.model;

import java.util.Arrays;
import java.util.Collection;

public class ChannelLockoutPolicies {

    private final String channelId;
    private final Collection<LockoutPolicy> policies;

    public ChannelLockoutPolicies(final String channelId, final LockoutPolicy... policies) {
        this(channelId, Arrays.asList(policies));
    }

    public ChannelLockoutPolicies(final String channelId, final Collection<LockoutPolicy> policies) {
        this.channelId = channelId;
        this.policies = policies;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public LockoutPolicy getPolicyFor(final LockoutStateRequest request) {
        return policies.stream()
                .filter(policy -> policy.appliesTo(request))
                .findFirst()
                .orElseThrow(() -> new LockoutPolicyNotConfiguredForRequestException(request));
    }

    public static class LockoutPolicyNotConfiguredForRequestException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "no policy configured for activity %s and alias %s";

        private final LockoutStateRequest request;

        public LockoutPolicyNotConfiguredForRequestException(final LockoutStateRequest request) {
            super(buildMessage(request));
            this.request = request;
        }

        public String getActivityType() {
            return request.getActivityType();
        }

        public String getAliasTypeName() {
            return request.getAliasTypeName();
        }

        private static String buildMessage(final LockoutStateRequest request) {
            return String.format(MESSAGE_FORMAT, request.getActivityType(), request.getAliasTypeName());
        }

    }

}
