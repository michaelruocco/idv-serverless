package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.service.TimeService;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationAttemptsConverterTest {

    private final TimeService timeService = mock(TimeService.class);

    private final VerificationAttemptsConverter converter = new VerificationAttemptsConverter(timeService);

    @Test
    public void shouldConverterToRequestWithCurrentTime() {
        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final CalculateLockoutStateRequest request = converter.toRequest(attempts);

        assertThat(request.getTimestamp()).isEqualTo(now);
    }

    @Test
    public void shouldConverterToRequestWithVerificationAttempts() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final CalculateLockoutStateRequest request = converter.toRequest(attempts);

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

}
