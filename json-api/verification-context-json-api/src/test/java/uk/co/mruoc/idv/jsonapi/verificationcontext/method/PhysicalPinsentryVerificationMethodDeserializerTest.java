package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.jsonapi.verificationcontext.ObjectMapperSingleton;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class PhysicalPinsentryVerificationMethodDeserializerTest {

    private static final String JSON = JsonLoader.loadJson("/method/physical-pinsentry-verification-method.json");
    private static final VerificationMethod METHOD = buildMethod();
    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeMethod() throws JsonProcessingException {
        final String json = MAPPER.writeValueAsString(METHOD);

        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeMethod() throws IOException {
        final VerificationMethod method = MAPPER.readValue(JSON, PhysicalPinsentryVerificationMethod.class);

        assertThat(method).isEqualToComparingFieldByFieldRecursively(METHOD);
    }

    private static VerificationMethod buildMethod() {
        final int duration = 300000;
        final PinsentryFunction function = PinsentryFunction.IDENTIFY;
        final Collection<CardNumber> cardNumbers = Arrays.asList(
                buildCardNumber1(),
                buildCardNumber2()
        );
        return new PhysicalPinsentryVerificationMethod(duration, function, cardNumbers);
    }

    private static CardNumber buildCardNumber1() {
        return CardNumber.builder()
                .tokenized("4324787978541234")
                .masked("************1234")
                .build();
    }

    private static CardNumber buildCardNumber2() {
        return CardNumber.builder()
                .tokenized("4324787978544321")
                .masked("************4321")
                .build();
    }

}
