package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class AliasDeserializerTest {

    private static final String JSON = loadContentFromClasspath("/alias.json");
    private static final Alias ALIAS = buildAlias();
    private static final ObjectMapper MAPPER = IdentityObjectMapperSingleton.get();

    @Test
    public void shouldSerialize() throws JsonProcessingException, JSONException {
        final String json = MAPPER.writeValueAsString(ALIAS);

        JSONAssert.assertEquals(json, JSON, JSONCompareMode.STRICT);
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
