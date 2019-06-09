package uk.co.mruoc.idv.json.identity;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.json.JsonConverter;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class AliasDeserializerTest {

    private static final String JSON = loadContentFromClasspath("/alias.json");
    private static final Alias ALIAS = buildAlias();

    private final JsonConverter converter = new IdentityJsonConverterFactory().build();

    @Test
    public void shouldSerialize() {
        final String json = converter.toJson(ALIAS);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserialize() {
        final Alias alias = converter.toObject(JSON, Alias.class);

        assertThat(alias).isEqualToComparingFieldByFieldRecursively(ALIAS);
    }

    private static Alias buildAlias() {
        return new TokenizedCreditCardNumberAlias("3489347343788005");
    }

}
