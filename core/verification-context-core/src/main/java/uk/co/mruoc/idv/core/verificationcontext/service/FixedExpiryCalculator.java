package uk.co.mruoc.idv.core.verificationcontext.service;

import java.time.Duration;
import java.time.Instant;

public class FixedExpiryCalculator implements ExpiryCalculator {

    private static final Duration FIVE_MINUTES = Duration.ofMinutes(5);

    @Override
    public Instant calculateExpiry(final Instant created) {
        return created.plus(FIVE_MINUTES);
    }

}
