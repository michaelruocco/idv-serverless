package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.json.verificationcontext.ObjectMapperSingleton;

public class JsonApiObjectMapperSingleton {

    private static final ObjectMapper MAPPER = buildMapper();

    private JsonApiObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = ObjectMapperSingleton.get();
        mapper.addMixIn(VerificationContext.class, JsonApiVerificationContextMixin.class);
        return mapper;
    }

}
