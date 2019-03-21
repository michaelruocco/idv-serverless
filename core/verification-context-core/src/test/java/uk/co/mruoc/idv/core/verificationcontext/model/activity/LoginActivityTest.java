package uk.co.mruoc.idv.core.verificationcontext.model.activity;

import org.junit.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginActivityTest {

    private static final String PROPERTY_NAME_1 = "property1";
    private static final String PROPERTY_NAME_2 = "property2";

    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";

    private static final Instant TIMESTAMP = Instant.now();

    private final Activity activity = new LoginActivity(TIMESTAMP, buildProperties());

    @Test
    public void shouldReturnType() {
        final String type = activity.getType();

        assertThat(type).isEqualTo(Activity.Types.LOGIN);
    }

    @Test
    public void shouldReturnTimestamp() {
        final Instant timestamp = activity.getTimestamp();

        assertThat(timestamp).isEqualTo(TIMESTAMP);
    }

    @Test
    public void shouldReturnPropertyValues() {
        final String value1 = activity.get(PROPERTY_NAME_1, String.class);
        final String value2 = activity.get(PROPERTY_NAME_2, String.class);

        assertThat(value1).isEqualTo(VALUE1);
        assertThat(value2).isEqualTo(VALUE2);
    }

    @Test
    public void shouldReturnEmptyPropertiesIfNotProvided() {
        final Activity activity = new LoginActivity(Instant.now());

        assertThat(activity.getProperties()).isEmpty();
    }

    private Map<String, Object> buildProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(PROPERTY_NAME_1, VALUE1);
        properties.put(PROPERTY_NAME_2, VALUE2);
        return Collections.unmodifiableMap(properties);
    }

}
