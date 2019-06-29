package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultRegisterAttemptRequestTest {

    @Test
    public void shouldSetMethodName() {
        final String methodName = "methodName";

        final RegisterAttemptRequest request = DefaultRegisterAttemptRequest.builder()
                .methodName(methodName)
                .build();

        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

    @Test
    public void shouldReturnVerificationId() {
        final UUID verificationId = UUID.randomUUID();

        final RegisterAttemptRequest request = DefaultRegisterAttemptRequest.builder()
                .verificationId(verificationId)
                .build();

        assertThat(request.getVerificationId()).isEqualTo(verificationId);
    }

    @Test
    public void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final RegisterAttemptRequest request = DefaultRegisterAttemptRequest.builder()
                .timestamp(timestamp)
                .build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    public void shouldReturnSuccessful() {
        final boolean successful = true;

        final RegisterAttemptRequest request = DefaultRegisterAttemptRequest.builder()
                .successful(successful)
                .build();

        assertThat(request.isSuccessful()).isEqualTo(successful);
    }

    @Test
    public void shouldHaveNoArgsConstructorForJackson() {
        final RegisterAttemptRequest request = new DefaultRegisterAttemptRequest();

        assertThat(request).isNotNull();
    }

}