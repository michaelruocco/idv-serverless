package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultActivityTest {

    private static final String TYPE = "type";
    private static final Instant TIMESTAMP = Instant.now();

    @Test
    public void shouldReturnType() {
        final Activity activity = new DefaultActivity(TYPE, TIMESTAMP);

        final String type = activity.getType();

        assertThat(type).isEqualTo(TYPE);
    }

    @Test
    public void shouldReturnTimestamp() {
        final Activity activity = new DefaultActivity(TYPE, TIMESTAMP);

        final Instant timestamp = activity.getTimestamp();

        assertThat(timestamp).isEqualTo(TIMESTAMP);
    }

    @Test
    public void shouldReturnNullValueIfGenericPropertyDoesNotExist() {
        final Activity activity = new DefaultActivity(TYPE, TIMESTAMP);

        final String value = activity.get("value", String.class);

        assertThat(value).isNull();
    }

    @Test
    public void shouldReturnGenericPropertyValue() {
        final String genericPropertyName = "propertyName";
        final String expectedValue = "property value";
        final Map<String, Object> genericProperties = new HashMap<>();
        genericProperties.put(genericPropertyName, expectedValue);
        final Activity activity = new DefaultActivity(TYPE, TIMESTAMP, genericProperties);

        final String value = activity.get(genericPropertyName, String.class);

        assertThat(value).isEqualTo(expectedValue);
    }

}
