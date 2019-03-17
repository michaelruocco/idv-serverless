package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {

    private static final ObjectMapper MAPPER = buildMapper();

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvIdentityModule());
        return mapper;
    }

}
