package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutdecision.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.service.TimeService;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationAttemptsConverterTest {

    private final Alias alias = mock(Alias.class);
    private final TimeService timeService = mock(TimeService.class);

    private final VerificationAttemptsConverter converter = new VerificationAttemptsConverter(timeService);

    @Test
    public void shouldConvertToRequestWithCurrentTime() {
        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final CalculateLockoutStateRequest request = converter.toRequest(alias, attempts);

        assertThat(request.getTimestamp()).isEqualTo(now);
    }

    @Test
    public void shouldConvertToRequestWithVerificationAttempts() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final CalculateLockoutStateRequest request = converter.toRequest(alias, attempts);

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

    @Test
    public void shouldConvertToRequestWithAlias() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final CalculateLockoutStateRequest request = converter.toRequest(alias, attempts);

        assertThat(request.getAlias()).isEqualTo(alias);
    }

}
