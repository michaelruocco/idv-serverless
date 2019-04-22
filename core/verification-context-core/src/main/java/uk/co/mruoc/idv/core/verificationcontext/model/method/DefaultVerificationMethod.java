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
    private final int maxAttempts;
    private final Map<String, Object> properties;

    public DefaultVerificationMethod(final String name) {
        this(name, DEFAULT_DURATION, DEFAULT_STATUS, DEFAULT_MAX_ATTEMPTS);
    }

    public DefaultVerificationMethod(final String name, final int duration) {
        this(name, duration, DEFAULT_STATUS, DEFAULT_MAX_ATTEMPTS);
    }

    public DefaultVerificationMethod(final String name, final VerificationStatus status) {
        this(name, DEFAULT_DURATION, status, DEFAULT_MAX_ATTEMPTS);
    }

    public DefaultVerificationMethod(final String name,
                                     final int duration,
                                     final VerificationStatus status,
                                     final int maxAttempts) {
        this(name, duration, status, maxAttempts, Collections.emptyMap());
    }

    public DefaultVerificationMethod(final String name,
                                     final int duration,
                                     final VerificationStatus status,
                                     final int maxAttempts,
                                     final Map<String, Object> properties) {
        this.name = name;
        this.duration = duration;
        this.status = status;
        this.maxAttempts = maxAttempts;
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
