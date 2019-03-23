package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IdentityObjectMapperSingleton {

    private static final ObjectMapper MAPPER = buildMapper();

    private IdentityObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvIdentityModule());
        return mapper;
    }

}
