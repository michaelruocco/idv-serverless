package uk.co.mruoc.idv.events;

import java.time.Instant;

public interface Event {

    String getType();

    Instant getTimestamp();

    Object getData();

}
