package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationSequencePolicyTest {

    private static final RegisterAttemptStrategy DEFAULT_REGISTER_ATTEMPT_STRATEGY = RegisterAttemptStrategy.IMMEDIATE;
    private static final String CUSTOM_NAME = "CUSTOM_NAME";

    @Test
    public void shouldReturnMethodNameIfContainsSingleMethod() {
        final VerificationMethodPolicy policy = new PushNotificationMethodPolicy();

        final VerificationSequencePolicy entry = new VerificationSequencePolicy(policy);

        assertThat(entry.getName()).isEqualTo(policy.getMethodName());
    }

    @Test
    public void shouldReturnName() {
        final VerificationMethodPolicy policy = new PushNotificationMethodPolicy();

        final VerificationSequencePolicy entry = new VerificationSequencePolicy(CUSTOM_NAME, policy);

        assertThat(entry.getName()).isEqualTo(CUSTOM_NAME);
    }

    @Test
    public void shouldReturnDefaultRegisterAttemptStrategyIfNotSet() {
        final VerificationMethodPolicy policy = new PushNotificationMethodPolicy();

        final VerificationSequencePolicy entry = new VerificationSequencePolicy(CUSTOM_NAME, policy);

        assertThat(entry.getRegisterAttemptStrategy()).isEqualTo(DEFAULT_REGISTER_ATTEMPT_STRATEGY);
    }

    @Test
    public void shouldReturnRegisterAttemptStrategy() {
        final VerificationMethodPolicy policy = new PushNotificationMethodPolicy();
        final RegisterAttemptStrategy registerAttemptStrategy = RegisterAttemptStrategy.ON_COMPLETION;

        final VerificationSequencePolicy entry = new VerificationSequencePolicy(CUSTOM_NAME, registerAttemptStrategy, policy);

        assertThat(entry.getRegisterAttemptStrategy()).isEqualTo(registerAttemptStrategy);
    }

    @Test
    public void shouldReturnNamePolicies() {
        final VerificationMethodPolicy pushNotificationPolicy = new PushNotificationMethodPolicy();
        final VerificationMethodPolicy cardCredentialsPolicy = new CardCredentialsMethodPolicy();

        final VerificationSequencePolicy entry = new VerificationSequencePolicy(CUSTOM_NAME, pushNotificationPolicy, cardCredentialsPolicy);

        assertThat(entry.getMethods()).containsExactly(pushNotificationPolicy, cardCredentialsPolicy);
    }

    @Test
    public void shouldPrintAllValues() {
        final VerificationMethodPolicy policy = new PushNotificationMethodPolicy();

        assertThat(policy.toString()).isEqualTo("VerificationMethodPolicy(" +
                "methodName=PUSH_NOTIFICATION, duration=300000, maximumAttempts=1)");
    }

}
