package uk.co.mruoc.idv.core.service;

import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultTimeServiceTest {

    private final TimeService service = new DefaultTimeService();

    @Test
    public void shouldReturnCurrentInstant() {
        final Instant expectedNow = Instant.now();

        final Instant actualNow = service.now();

        final Instant start = expectedNow.minusSeconds(1);
        final Instant end = expectedNow.plusSeconds(1);
        assertThat(actualNow).isStrictlyBetween(start, end);
    }

}
