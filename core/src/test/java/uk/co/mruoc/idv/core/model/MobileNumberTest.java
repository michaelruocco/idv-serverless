package uk.co.mruoc.idv.core.model;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MobileNumberTest {

    private static final UUID ID = UUID.fromString("2141f495-af39-4b77-b25b-e1afed7ec7e7");
    private static final String MASKED = "*******456";

    @Test
    public void shouldMaskLeavingLastThreeCharactersUnmasked() {
        final String number = "1234567456";

        final String masked = MobileNumber.mask(number);

        assertThat(masked).isEqualTo(MASKED);
    }

    @Test
    public void shouldDefaultAllValuesToNull() {
        final MobileNumber mobileNumber = MobileNumber.builder().build();

        assertThat(mobileNumber.getId()).isNull();
        assertThat(mobileNumber.getMasked()).isNull();
    }

    @Test
    public void shouldSetId() {
        final MobileNumber mobileNumber = MobileNumber.builder()
                .id(ID)
                .build();

        assertThat(mobileNumber.getId()).isEqualTo(ID);
    }

    @Test
    public void shouldSetMaskedValue() {
        final MobileNumber mobileNumber = MobileNumber.builder()
                .masked(MASKED)
                .build();

        assertThat(mobileNumber.getMasked()).isEqualTo(MASKED);
    }

    @Test
    public void shouldPrintAllValues() {
        final MobileNumber mobileNumber = MobileNumber.builder()
                .id(ID)
                .masked(MASKED)
                .build();

        final String value = mobileNumber.toString();

        assertThat(value).isEqualTo("MobileNumber(id=2141f495-af39-4b77-b25b-e1afed7ec7e7, masked=*******456)");
    }

}
