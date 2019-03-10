package uk.co.mruoc.idv.core.verificationcontext.service;

import java.time.Instant;

public interface ExpiryCalculator {

    Instant calculateExpiry(final Instant created);

}
