package uk.co.mruoc.idv.core.service;

import java.time.Instant;

public class DefaultTimeService implements TimeService {

    @Override
    public Instant now() {
        return Instant.now();
    }

}
