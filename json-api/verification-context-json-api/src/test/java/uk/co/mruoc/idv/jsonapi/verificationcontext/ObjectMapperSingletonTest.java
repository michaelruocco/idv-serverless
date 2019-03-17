package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectMapperSingletonTest {

    @Test
    public void shouldAlwaysReturnSameMapper() {
        final ObjectMapper mapper1 = ObjectMapperSingleton.get();
        final ObjectMapper mapper2 = ObjectMapperSingleton.get();

        assertThat(mapper1).isEqualTo(mapper2);
    }

    @Test
    public void shouldRegisterModules() {
        final ObjectMapper mapper = ObjectMapperSingleton.get();

        assertThat(mapper.getRegisteredModuleIds()).containsExactly(
                "uk.co.mruoc.idv.jsonapi.identity.IdvIdentityModule",
                "uk.co.mruoc.idv.jsonapi.verificationcontext.IdvVerificationContextModule",
                "com.fasterxml.jackson.datatype.jsr310.JavaTimeModule",
                "org.zalando.jackson.datatype.money.MoneyModule"
        );
    }

}
