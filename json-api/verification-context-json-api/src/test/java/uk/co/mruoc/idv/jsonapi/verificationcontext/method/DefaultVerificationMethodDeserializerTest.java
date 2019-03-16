package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.jsonapi.verificationcontext.ObjectMapperSingleton;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultVerificationMethodDeserializerTest {

    private static final String JSON = JsonLoader.loadJson("/method/default-verification-method.json");
    private static final VerificationMethod METHOD = buildMethod();
    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeMethod() throws JsonProcessingException {
        final String json = MAPPER.writeValueAsString(METHOD);

        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeMethod() throws IOException {
        final VerificationMethod method = MAPPER.readValue(JSON, DefaultVerificationMethod.class);

        assertThat(method).isEqualToComparingFieldByFieldRecursively(METHOD);
    }

    private static VerificationMethod buildMethod() {
        final Map<String, Object> subProperty = new HashMap<>();
        subProperty.put("subProperty", "value");

        final Map<String, Object> properties = new HashMap<>();
        properties.put("property", 1);
        properties.put("anotherProperty", Collections.unmodifiableMap(subProperty));

        final int duration = 300000;
        final String name = "DEFAULT_METHOD";
        return new DefaultVerificationMethod(name, duration, Collections.unmodifiableMap(properties));
    }

}
