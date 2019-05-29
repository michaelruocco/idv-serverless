package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;

@ToString
@Getter
public class DefaultVerificationMethod implements VerificationMethod {


    private final String name;
    private final int duration;
    private final VerificationStatus status;
    private final Map<String, Object> properties;

    public DefaultVerificationMethod(final String name) {
        this(name, DEFAULT_DURATION, DEFAULT_STATUS);
    }

    public DefaultVerificationMethod(final String name, final int duration) {
        this(name, duration, DEFAULT_STATUS);
    }

    public DefaultVerificationMethod(final String name, final VerificationStatus status) {
        this(name, DEFAULT_DURATION, status);
    }

    public DefaultVerificationMethod(final String name,
                                     final int duration,
                                     final VerificationStatus status) {
        this(name, duration, status, Collections.emptyMap());
    }

    public DefaultVerificationMethod(final String name,
                                     final int duration,
                                     final VerificationStatus status,
                                     final Map<String, Object> properties) {
        this.name = name;
        this.duration = duration;
        this.status = status;
        this.properties = properties;
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
