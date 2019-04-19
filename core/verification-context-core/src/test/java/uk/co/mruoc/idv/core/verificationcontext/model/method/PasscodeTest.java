package uk.co.mruoc.idv.core.verificationcontext.model.method;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasscodeTest {

    @Test
    public void shouldReturnLength() {
        final int length = 8;

        final Passcode passcode = Passcode.builder()
                .length(length)
                .build();

        assertThat(passcode.getLength()).isEqualTo(length);
    }

    @Test
    public void shouldReturnDuration() {
        final int duration = 300000;

        final Passcode passcode = Passcode.builder()
                .duration(duration)
                .build();

        assertThat(passcode.getDuration()).isEqualTo(duration);
    }

    @Test
    public void shouldReturnMaxAttempts() {
        final int maxAttempts = 2;

        final Passcode passcode = Passcode.builder()
                .maxAttempts(maxAttempts)
                .build();

        assertThat(passcode.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    public void shouldPrintAllValues() {
        final Passcode passcode = Passcode.builder()
                .maxAttempts(3)
                .length(8)
                .duration(150000)
                .build();

        final String value = passcode.toString();

        assertThat(value).isEqualTo("Passcode(length=8, duration=150000, maxAttempts=3)");
    }

}
