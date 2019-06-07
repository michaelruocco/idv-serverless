package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.idv.json.JacksonJsonConverter;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverterFactory;
import uk.co.mruoc.idv.json.identity.IdvIdentityModule;
import uk.co.mruoc.idv.json.verificationcontext.IdvVerificationContextModule;

public class JsonApiVerificationContextJsonConverterFactory implements JsonConverterFactory {

    private static final ObjectMapper MAPPER = buildMapper();

    public JsonConverter build() {
        return new JacksonJsonConverter(MAPPER);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvIdentityModule());
        mapper.registerModule(new IdvVerificationContextModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new IdvJsonApiVerificationContextModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
