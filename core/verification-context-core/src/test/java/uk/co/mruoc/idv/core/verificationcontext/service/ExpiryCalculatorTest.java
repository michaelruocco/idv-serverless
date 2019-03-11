package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpiryCalculatorTest {

    private static final Duration FIVE_MINUTES = Duration.ofMinutes(5);

    private final ExpiryCalculator calculator = new FixedExpiryCalculator();

    @Test
    public void shouldCalculateExpiryInstant() {
        final Instant created = Instant.now();

        final Instant expiry = calculator.calculateExpiry(created);

        assertThat(expiry).isEqualTo(created.plus(FIVE_MINUTES));
    }
}
