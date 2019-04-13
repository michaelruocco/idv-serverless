package uk.co.mruoc.idv.jsonapi.authorizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.json.JacksonJsonConverter;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverterFactory;
import uk.co.mruoc.idv.json.authorizer.AuthorizerModule;

public class GenerateTokenJsonConverterFactory implements JsonConverterFactory {

    private static final ObjectMapper MAPPER = buildMapper();

    public JsonConverter build() {
        return new JacksonJsonConverter(MAPPER);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new AuthorizerModule());
        return mapper;
    }

}
