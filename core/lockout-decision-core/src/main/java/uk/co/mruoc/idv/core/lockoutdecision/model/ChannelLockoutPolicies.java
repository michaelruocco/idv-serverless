package uk.co.mruoc.idv.core.lockoutdecision.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

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

    public LockoutPolicy getPolicyFor(final VerificationAttempt attempt) {
        return policies.stream()
                .filter(policy -> policy.appliesTo(attempt))
                .findFirst()
                .orElseThrow(() -> new LockoutPolicyNotConfiguredForAttemptException(attempt));
    }

    public static class LockoutPolicyNotConfiguredForAttemptException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "no policy configured for activity %s and alias %s";

        private final VerificationAttempt attempt;

        public LockoutPolicyNotConfiguredForAttemptException(final VerificationAttempt attempt) {
            super(buildMessage(attempt));
            this.attempt = attempt;
        }

        public String getActivityType() {
            return attempt.getActivityType();
        }

        public String getAliasTypeName() {
            return attempt.getAliasTypeName();
        }

        public Optional<String> getMethodName() {
            return attempt.getMethodName();
        }

        private static String buildMessage(final VerificationAttempt attempt) {
            final StringBuilder message = new StringBuilder(String.format(MESSAGE_FORMAT, attempt.getActivityType(), attempt.getAliasTypeName()));
            if (attempt.getMethodName().isPresent()) {
                return message.append(String.format(" and method %s", attempt.getMethodName().get())).toString();
            }
            return message.toString();
        }

    }

}
