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
    public void shouldReturnAttempts() {
        final int attempts = 2;

        final Passcode passcode = Passcode.builder()
                .attempts(attempts)
                .build();

        assertThat(passcode.getAttempts()).isEqualTo(attempts);
    }

}
