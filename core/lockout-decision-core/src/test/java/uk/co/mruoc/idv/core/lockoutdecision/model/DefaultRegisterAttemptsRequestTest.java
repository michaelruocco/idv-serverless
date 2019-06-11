package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultRegisterAttemptsRequestTest {

    @Test
    public void shouldSetMethodName() {
        final UUID contextId = UUID.randomUUID();

        final RegisterAttemptsRequest request = DefaultRegisterAttemptsRequest.builder()
                .contextId(contextId)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    public void shouldReturnVerificationId() {
        final Collection<RegisterAttemptRequest> attempts = Collections.emptyList();

        final RegisterAttemptsRequest request = DefaultRegisterAttemptsRequest.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

    @Test
    public void shouldHaveNoArgsConstructorForJackson() {
        final RegisterAttemptsRequest request = new DefaultRegisterAttemptsRequest();

        assertThat(request).isNotNull();
    }

}