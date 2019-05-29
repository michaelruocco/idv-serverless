package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService.VerificationContextExpiredException;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationContextExpiredExceptionTest {

    private final UUID contextId = UUID.randomUUID();
    private final Instant expiry = Instant.now();

    private final VerificationContextExpiredException exception = new VerificationContextExpiredException(contextId, expiry);

    @Test
    public void shouldReturnMessageWithContextIdValue() {
        assertThat(exception.getMessage()).isEqualTo(contextId.toString());
    }

    @Test
    public void shouldReturnExpiry() {
        assertThat(exception.getExpiry()).isEqualTo(expiry);
    }

}
