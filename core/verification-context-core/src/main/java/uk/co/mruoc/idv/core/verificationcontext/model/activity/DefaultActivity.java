package uk.co.mruoc.idv.core.verificationcontext.model.activity;

import lombok.ToString;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString
public class DefaultActivity implements Activity {

    private final String type;
    private final Instant timestamp;
    private final Map<String, Object> properties;

    public DefaultActivity(final String type, final Instant timestamp) {
        this(type, timestamp, new HashMap<>());
    }

    public DefaultActivity(final String type, final Instant timestamp, final Map<String, Object> properties) {
        this.type = type;
        this.timestamp = timestamp;
        this.properties = properties;
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
    public Map<String, Object> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public <T> T get(final String name, final Class<T> type) {
        final Object item = properties.get(name);
        return type.cast(item);
    }

}
