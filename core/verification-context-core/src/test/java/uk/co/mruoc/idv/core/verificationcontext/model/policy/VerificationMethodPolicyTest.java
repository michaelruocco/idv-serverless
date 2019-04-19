package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationMethodPolicyTest {

    private static final int DEFAULT_DURATION = 300000;
    private static final int DEFAULT_MAX_ATTEMPTS = 1;

    private static final String METHOD_NAME = "METHOD_NAME";
    private static final int DURATION = 150000;
    private static final int MAX_ATTEMPTS = 3;

    @Test
    public void shouldReturnDefaultDurationIfNotSpecified() {
        final VerificationMethodPolicy policy = new VerificationMethodPolicy(METHOD_NAME);

        assertThat(policy.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    public void shouldReturnDefaultMaximumAttemptsIfNotSpecified() {
        final VerificationMethodPolicy policy = new VerificationMethodPolicy(METHOD_NAME);

        assertThat(policy.getMaximumAttempts()).isEqualTo(DEFAULT_MAX_ATTEMPTS);
    }

    @Test
    public void shouldReturnName() {
        final VerificationMethodPolicy policy = new VerificationMethodPolicy(METHOD_NAME);

        assertThat(policy.getMethodName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldReturnSpecificDuration() {
        final VerificationMethodPolicy policy = new VerificationMethodPolicy(METHOD_NAME, DURATION);

        assertThat(policy.getDuration()).isEqualTo(DURATION);
    }

    @Test
    public void shouldReturnMaxmimumAttemptsDuration() {
        final VerificationMethodPolicy policy = new VerificationMethodPolicy(METHOD_NAME, DURATION, MAX_ATTEMPTS);

        assertThat(policy.getMaximumAttempts()).isEqualTo(MAX_ATTEMPTS);
    }

}