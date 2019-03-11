package uk.co.mruoc.idv.core.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CardNumberTest {

    private static final String ENCRYPTED = "DNSNDSADNMdaeqeqd332e3da";
    private static final String TOKENIZED = "1234567890123456";
    private static final String MASKED = "************3456";

    @Test
    public void shouldMaskLeavingLastFourCharactersUnmasked() {
        final String masked = CardNumber.mask(TOKENIZED);

        assertThat(masked).isEqualTo(MASKED);
    }

    @Test
    public void shouldDefaultAllValuesToNull() {
        final CardNumber cardNumber = CardNumber.builder().build();

        assertThat(cardNumber.getMasked()).isNull();
        assertThat(cardNumber.getEncrypted()).isNull();
        assertThat(cardNumber.getTokenized()).isNull();
    }

    @Test
    public void shouldSetEncryptedValue() {
        final CardNumber cardNumber = CardNumber.builder()
                .encrypted(ENCRYPTED)
                .build();

        assertThat(cardNumber.getEncrypted()).isEqualTo(ENCRYPTED);
    }

    @Test
    public void shouldSetTokenizedValue() {
        final CardNumber cardNumber = CardNumber.builder()
                .tokenized(TOKENIZED)
                .build();

        assertThat(cardNumber.getTokenized()).isEqualTo(TOKENIZED);
    }

    @Test
    public void shouldSetMaskedValue() {
        final CardNumber cardNumber = CardNumber.builder()
                .masked(MASKED)
                .build();

        assertThat(cardNumber.getMasked()).isEqualTo(MASKED);
    }

    @Test
    public void shouldFilterSensitiveValues() {
        final CardNumber cardNumber = CardNumber.builder()
                .encrypted(ENCRYPTED)
                .tokenized(TOKENIZED)
                .masked(MASKED)
                .build();

        final CardNumber filteredCardNumber = cardNumber.filterSensitiveValues();

        assertThat(filteredCardNumber.getMasked()).isEqualTo(cardNumber.getMasked());
        assertThat(filteredCardNumber.getEncrypted()).isNull();
        assertThat(filteredCardNumber.getTokenized()).isNull();
    }

}
