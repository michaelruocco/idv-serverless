package uk.co.mruoc.idv.jsonapi.authorizer;

import org.junit.Test;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverterFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class GenerateTokenJsonConverterFactoryTest {

    private final JsonConverterFactory factory = new GenerateTokenJsonConverterFactory();

    @Test
    public void shouldCreateJsonConverter() {
        final JsonConverter converter = factory.build();

        assertThat(converter).isNotNull();
    }

}
