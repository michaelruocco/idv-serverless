package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.jsonapi.verificationcontext.ObjectMapperSingleton;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PushNotificationVerificationMethodDeserializerTest {

    private static final String JSON = JsonLoader.loadJson("/method/push-notification-verification-method.json");
    private static final VerificationMethod METHOD = buildMethod();
    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeMethod() throws JsonProcessingException {
        final String json = MAPPER.writeValueAsString(METHOD);

        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeMethod() throws IOException {
        final VerificationMethod method = MAPPER.readValue(JSON, PushNotificationVerificationMethod.class);

        assertThat(method).isEqualToComparingFieldByFieldRecursively(METHOD);
    }

    private static VerificationMethod buildMethod() {
        final int duration = 300000;
        return new PushNotificationVerificationMethod(duration);
    }

}
