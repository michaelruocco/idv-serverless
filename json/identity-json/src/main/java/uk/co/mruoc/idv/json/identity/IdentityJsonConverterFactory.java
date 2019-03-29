package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.json.JacksonJsonConverter;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverterFactory;

public class IdentityJsonConverterFactory implements JsonConverterFactory {

    private static final ObjectMapper MAPPER = buildMapper();

    public JsonConverter build() {
        return new JacksonJsonConverter(MAPPER);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvIdentityModule());
        return mapper;
    }

}
