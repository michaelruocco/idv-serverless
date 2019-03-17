package uk.co.mruoc.idv.jsonapi.verificationcontext.activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.jsonapi.verificationcontext.ObjectMapperSingleton;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultActivityDeserializerTest {

    private static final String JSON = JsonLoader.loadJson("/activity/default-activity.json");
    private static final Activity ACTIVITY = buildActivity();
    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeActivity() throws JsonProcessingException  {
        final String json = MAPPER.writeValueAsString(ACTIVITY);

        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeActivity() throws IOException {
        final Activity activity = MAPPER.readValue(JSON, DefaultActivity.class);

        assertThat(activity).isEqualToComparingFieldByFieldRecursively(ACTIVITY);
    }

    private static Activity buildActivity() {
        final Map<String, Object> subProperty = new HashMap<>();
        subProperty.put("subProperty", "value");

        final Map<String, Object> properties = new HashMap<>();
        properties.put("property", 1);
        properties.put("anotherProperty", Collections.unmodifiableMap(subProperty));

        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        return new DefaultActivity("DEFAULT_ACTIVITY", timestamp, Collections.unmodifiableMap(properties));
    }

}
