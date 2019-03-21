package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasDeserializerTest {

    private static final String JSON = JsonLoader.loadJson("/alias.json");
    private static final Alias ALIAS = buildAlias();
    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerialize() throws JsonProcessingException {
        final String json = MAPPER.writeValueAsString(ALIAS);

        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserialize() throws IOException {
        final Alias alias = MAPPER.readValue(JSON, Alias.class);

        assertThat(alias).isEqualToComparingFieldByFieldRecursively(ALIAS);
    }

    private static Alias buildAlias() {
        return new TokenizedCreditCardNumberAlias("3489347343788005");
    }

}
