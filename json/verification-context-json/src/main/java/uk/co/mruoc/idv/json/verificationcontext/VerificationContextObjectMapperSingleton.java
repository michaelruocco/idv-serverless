package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.idv.json.identity.IdentityObjectMapperSingleton;

public class VerificationContextObjectMapperSingleton {

    private static final ObjectMapper MAPPER = buildMapper();

    private VerificationContextObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = IdentityObjectMapperSingleton.get();
        mapper.registerModule(new IdvVerificationContextModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new MoneyModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
