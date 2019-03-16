package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString
public class DefaultVerificationMethod implements VerificationMethod {

    private final String name;
    private final int duration;
    private final Map<String, Object> properties;

    public DefaultVerificationMethod(final String name) {
        this(name, DEFAULT_DURATION);
    }

    public DefaultVerificationMethod(final String name, final int duration) {
        this(name, duration, new HashMap<>());
    }

    public DefaultVerificationMethod(final String name, final int duration, final Map<String, Object> properties) {
        this.name = name;
        this.duration = duration;
        this.properties = properties;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public Map<String, Object> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public <T> T get(String name, Class<T> type) {
        final Object item = properties.get(name);
        return type.cast(item);
    }

}
