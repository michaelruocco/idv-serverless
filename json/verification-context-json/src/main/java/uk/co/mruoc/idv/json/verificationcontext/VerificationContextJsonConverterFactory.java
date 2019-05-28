package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.idv.json.JacksonJsonConverter;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverterFactory;
import uk.co.mruoc.idv.json.identity.IdvIdentityModule;
import uk.co.mruoc.idv.json.lockoutdecision.IdvLockoutDecisionModule;

public class VerificationContextJsonConverterFactory implements JsonConverterFactory {

    private final static ObjectMapper MAPPER = buildMapper();

    public JsonConverter build() {
        return new JacksonJsonConverter(MAPPER);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvIdentityModule());
        mapper.registerModule(new IdvVerificationContextModule());
        mapper.registerModule(new IdvLockoutDecisionModule());
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
