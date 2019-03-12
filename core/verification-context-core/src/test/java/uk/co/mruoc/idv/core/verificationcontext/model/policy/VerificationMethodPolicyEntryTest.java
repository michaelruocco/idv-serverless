package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationMethodPolicyEntryTest {

    private static final String CUSTOM_NAME = "CUSTOM_NAME";

    @Test
    public void shouldReturnMethodNameIfContainsSingleMethod() {
        final VerificationMethodPolicy policy = new PushNotificationMethodPolicy();

        final VerificationMethodPolicyEntry entry = new VerificationMethodPolicyEntry(policy);

        assertThat(entry.getName()).isEqualTo(policy.getMethodName());
    }

    @Test
    public void shouldReturnName() {
        final VerificationMethodPolicy policy = new PushNotificationMethodPolicy();

        final VerificationMethodPolicyEntry entry = new VerificationMethodPolicyEntry(CUSTOM_NAME, policy);

        assertThat(entry.getName()).isEqualTo(CUSTOM_NAME);
    }

    @Test
    public void shouldReturnNamePolicies() {
        final VerificationMethodPolicy pushNotificationPolicy = new PushNotificationMethodPolicy();
        final VerificationMethodPolicy cardCredentialsPolicy = new CardCredentialsMethodPolicy();

        final VerificationMethodPolicyEntry entry = new VerificationMethodPolicyEntry(CUSTOM_NAME, pushNotificationPolicy, cardCredentialsPolicy);

        assertThat(entry.getMethods()).containsExactly(pushNotificationPolicy, cardCredentialsPolicy);
    }

}
