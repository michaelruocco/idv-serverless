package uk.co.mruoc.idv.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonJsonConverter implements JsonConverter {

    private final ObjectMapper mapper;

    public JacksonJsonConverter(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String toJson(final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new JsonConversionException(e);
        }
    }

    @Override
    public <T> T toObject(final String json, final Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (final IOException e) {
            throw new JsonConversionException(e);
        }
    }

}
