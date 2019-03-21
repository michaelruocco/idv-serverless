package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonApiObjectMapperSingletonTest {

    @Test
    public void shouldRegisterJsonApiVerificationContextMixin() {
        final ObjectMapper mapper = JsonApiObjectMapperSingleton.get();

        final Class<?> mixinClass = mapper.findMixInClassFor(VerificationContext.class);

        assertThat(mixinClass.getName()).isEqualTo(JsonApiVerificationContextMixin.class.getName());
    }

}
