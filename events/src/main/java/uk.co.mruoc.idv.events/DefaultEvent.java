package uk.co.mruoc.idv.events;

import java.time.Instant;

public class DefaultEvent implements Event {

    private final String type;
    private final Instant timestamp;
    private final Object data;

    public DefaultEvent(final String type, final Instant timestamp, final Object data) {
        this.type = type;
        this.timestamp = timestamp;
        this.data = data;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public Object getData() {
        return data;
    }

}
