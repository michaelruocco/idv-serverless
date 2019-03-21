package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextObjectMapperSingleton;

public class JsonApiVerificationContextObjectMapperSingleton {

    private static final ObjectMapper MAPPER = buildMapper();

    private JsonApiVerificationContextObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = VerificationContextObjectMapperSingleton.get();
        mapper.addMixIn(VerificationContext.class, JsonApiVerificationContextMixin.class);
        return mapper;
    }

}
