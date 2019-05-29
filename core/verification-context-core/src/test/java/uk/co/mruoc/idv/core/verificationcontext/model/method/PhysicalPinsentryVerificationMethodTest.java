package uk.co.mruoc.idv.core.verificationcontext.model.method;

import org.junit.Test;
import uk.co.mruoc.idv.core.model.CardNumber;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class PhysicalPinsentryVerificationMethodTest {

    private static final int DURATION = 300000;
    private static final PinsentryFunction FUNCTION = PinsentryFunction.IDENTIFY;
    private static final Collection<CardNumber> CARD_NUMBERS = Arrays.asList(
            CardNumber.builder().tokenized("1234567890123456").build(),
            CardNumber.builder().tokenized("6543210987654321").build()
    );

    private final PhysicalPinsentryVerificationMethod physicalPinsentry = new PhysicalPinsentryVerificationMethod(DURATION, FUNCTION, CARD_NUMBERS);

    @Test
    public void shouldReturnName() {
        final String name = physicalPinsentry.getName();

        assertThat(name).isEqualTo(VerificationMethod.Names.PHYSICAL_PINSENTRY);
    }

    @Test
    public void shouldReturnDuration() {
        final int duration = physicalPinsentry.getDuration();

        assertThat(duration).isEqualTo(DURATION);
    }

    @Test
    public void shouldReturnFunction() {
        final PinsentryFunction function = physicalPinsentry.getFunction();

        assertThat(function).isEqualTo(FUNCTION);
    }

    @Test
    public void shouldReturnStatus() {
        final VerificationStatus status = physicalPinsentry.getStatus();

        assertThat(status).isEqualTo(VerificationMethod.DEFAULT_STATUS);
    }

    @Test
    public void shouldReturnCardNumbers() {
        final Collection<CardNumber> cardNumbers = physicalPinsentry.getCardNumbers();

        assertThat(cardNumbers).isEqualTo(CARD_NUMBERS);
    }

    @Test
    public void shouldPrintAllValues() {
        final String value = physicalPinsentry.toString();

        assertThat(value).isEqualTo("PhysicalPinsentryVerificationMethod(" +
                "super=DefaultVerificationMethod(" +
                "name=PHYSICAL_PINSENTRY, duration=300000, status=AVAILABLE, " +
                "properties={" +
                "cardNumbers=[CardNumber(masked=null, tokenized=1234567890123456, encrypted=null), " +
                "CardNumber(masked=null, tokenized=6543210987654321, encrypted=null)], function=IDENTIFY}))");
    }

}
