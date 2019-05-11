package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeBasedIntervalTest {

    private static final int NUMBER_OF_ATTEMPTS = 3;
    private static final Duration DURATION = Duration.ofMinutes(15);

    private final TimeBasedInterval interval = new TimeBasedInterval(NUMBER_OF_ATTEMPTS, DURATION);

    @Test
    public void shouldReturnNumberOfAttempts() {
        assertThat(interval.getNumberOfAttempts()).isEqualTo(NUMBER_OF_ATTEMPTS);
    }

    @Test
    public void shouldReturnDuration() {
        assertThat(interval.getDuration()).isEqualTo(DURATION);
    }

}
