package uk.co.mruoc.idv.awslambda.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.jsonapi.identity.IdvIdentityModule;

public class ObjectMapperSingleton {

    private static final ObjectMapper MAPPER = buildMapper();

    private ObjectMapperSingleton() {
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
