package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentityObjectMapperSingletonTest {

    @Test
    public void shouldAlwaysReturnSameMapper() {
        final ObjectMapper mapper1 = IdentityObjectMapperSingleton.get();
        final ObjectMapper mapper2 = IdentityObjectMapperSingleton.get();

        assertThat(mapper1).isEqualTo(mapper2);
    }

    @Test
    public void shouldRegisterModules() {
        final ObjectMapper mapper = IdentityObjectMapperSingleton.get();

        assertThat(mapper.getRegisteredModuleIds()).containsExactly("uk.co.mruoc.idv.json.identity.IdvIdentityModule");
    }

}
