package uk.co.mruoc.idv.core.verificationcontext.model.method;

import java.util.HashMap;
import java.util.Map;

public class DefaultVerificationMethod implements VerificationMethod {

    private final String name;
    private final int duration;
    private final Map<String, Object> genericProperties;

    public DefaultVerificationMethod(final String name, final int duration) {
        this(name, duration, new HashMap<>());
    }

    public DefaultVerificationMethod(final String name, final int duration, final Map<String, Object> genericProperties) {
        this.name = name;
        this.duration = duration;
        this.genericProperties = genericProperties;
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
    public <T> T get(String name, Class<T> type) {
        final Object item = genericProperties.get(name);
        return type.cast(item);
    }

}
