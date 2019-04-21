package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.event.VerificationContextCreatedEvent;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationContextConverterTest {

    private final TimeService timeService = mock(TimeService.class);

    private final VerificationContextConverter converter = new VerificationContextConverter(timeService);

    @Test
    public void shouldCreateEventWithCurrentTimestamp() {
        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);
        final VerificationContext context = mock(VerificationContext.class);

        final VerificationContextCreatedEvent event = converter.toCreatedEvent(context);

        assertThat(event.getTimestamp()).isEqualTo(now);
    }

    @Test
    public void shouldCreateEventWithContext() {
        final VerificationContext context = mock(VerificationContext.class);

        final VerificationContextCreatedEvent event = converter.toCreatedEvent(context);

        assertThat(event.getData()).isEqualTo(context);
    }

}
