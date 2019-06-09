package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CalculateLockoutStateRequestTest {

    @Test
    public void shouldSetAlias() {
        final Alias alias = mock(Alias.class);

        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .alias(alias)
                .build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    public void shouldSetAttempts() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

    @Test
    public void shouldSetTimestamp() {
        final Instant timestamp = Instant.now();

        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .timestamp(timestamp)
                .build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

}
