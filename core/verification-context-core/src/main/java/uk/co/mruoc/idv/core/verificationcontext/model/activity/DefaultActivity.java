package uk.co.mruoc.idv.core.verificationcontext.model.activity;

import lombok.ToString;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ToString
public class DefaultActivity implements Activity {

    private final String type;
    private final Instant timestamp;
    private final Map<String, Object> genericProperties;

    public DefaultActivity(final String type, final Instant timestamp) {
        this(type, timestamp, new HashMap<>());
    }

    public DefaultActivity(final String type, final Instant timestamp, final Map<String, Object> genericProperties) {
        this.type = type;
        this.timestamp = timestamp;
        this.genericProperties = genericProperties;
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
    public <T> T get(final String name, final Class<T> type) {
        final Object item = genericProperties.get(name);
        return type.cast(item);
    }

}
