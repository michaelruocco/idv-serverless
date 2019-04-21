package uk.co.mruoc.idv.core.verificationcontext.model.event;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class VerificationContextCreatedEventTest {

    @Test
    public void shouldReturnType() {
        final VerificationContextCreatedEvent event = VerificationContextCreatedEvent.builder()
                .build();

        assertThat(event.getType()).isEqualTo("VERIFICATION_CONTEXT_CREATED_EVENT");
    }

    @Test
    public void shouldReturnInstant() {
        final Instant timestamp = Instant.now();

        final VerificationContextCreatedEvent event = VerificationContextCreatedEvent.builder()
                .timestamp(timestamp)
                .build();

        assertThat(event.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    public void shouldReturnData() {
        final VerificationContext context = mock(VerificationContext.class);

        final VerificationContextCreatedEvent event = VerificationContextCreatedEvent.builder()
                .context(context)
                .build();

        assertThat(event.getData()).isEqualTo(context);
    }

}
