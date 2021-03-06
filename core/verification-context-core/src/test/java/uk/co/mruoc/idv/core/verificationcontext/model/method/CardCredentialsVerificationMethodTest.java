package uk.co.mruoc.idv.core.verificationcontext.model.method;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CardCredentialsVerificationMethodTest {

    private static final int DURATION = 300000;

    private final VerificationMethod cardCredentials = new CardCredentialsVerificationMethod(DURATION);

    @Test
    public void shouldReturnName() {
        final String name = cardCredentials.getName();

        assertThat(name).isEqualTo(VerificationMethod.Names.CARD_CREDENTIALS);
    }

    @Test
    public void shouldReturnDuration() {
        final int duration = cardCredentials.getDuration();

        assertThat(duration).isEqualTo(DURATION);
    }

    @Test
    public void shouldReturnEligible() {
        final boolean eligible = cardCredentials.isEligible();

        assertThat(eligible).isTrue();
    }

    @Test
    public void shouldPrintAllValues() {
        final String value = cardCredentials.toString();

        assertThat(value).isEqualTo("CardCredentialsVerificationMethod(super=" +
                "DefaultVerificationMethod(name=CARD_CREDENTIALS, duration=300000, eligible=true, " +
                "properties={}))");
    }

}
