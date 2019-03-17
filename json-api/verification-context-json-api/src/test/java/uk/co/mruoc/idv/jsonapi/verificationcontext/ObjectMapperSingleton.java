package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.idv.jsonapi.identity.IdvIdentityModule;

public class ObjectMapperSingleton {

    private static final ObjectMapper MAPPER = buildMapper();

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvIdentityModule());
        mapper.registerModule(new IdvVerificationContextModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new MoneyModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
