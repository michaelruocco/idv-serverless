package uk.co.mruoc.idv.core.verificationcontext.model.method;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MobilePinsentryVerificationMethodTest {

    private static final int DURATION = 300000;
    private static final PinsentryFunction FUNCTION = PinsentryFunction.IDENTIFY;

    private final MobilePinsentryVerificationMethod mobilePinsentry = new MobilePinsentryVerificationMethod(DURATION, FUNCTION);

    @Test
    public void shouldReturnName() {
        final String name = mobilePinsentry.getName();

        assertThat(name).isEqualTo(VerificationMethod.Names.MOBILE_PINSENTRY);
    }

    @Test
    public void shouldReturnDuration() {
        final int duration = mobilePinsentry.getDuration();

        assertThat(duration).isEqualTo(DURATION);
    }

    @Test
    public void shouldReturnStatus() {
        final VerificationStatus status = mobilePinsentry.getStatus();

        assertThat(status).isEqualTo(VerificationMethod.DEFAULT_STATUS);
    }

    @Test
    public void shouldReturnFunction() {
        final PinsentryFunction function = mobilePinsentry.getFunction();

        assertThat(function).isEqualTo(FUNCTION);
    }

    @Test
    public void shouldPrintAllValues() {
        final String value = mobilePinsentry.toString();

        assertThat(value).isEqualTo("MobilePinsentryVerificationMethod(super=" +
                "DefaultVerificationMethod(" +
                "name=MOBILE_PINSENTRY, duration=300000, status=AVAILABLE, " +
                "properties={function=IDENTIFY}))");
    }

}
