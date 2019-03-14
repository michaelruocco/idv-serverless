package uk.co.mruoc.idv.core.verificationcontext.model.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaPasscodeTest {

    private static final int LENGTH = 8;
    private static final int DURATION = 150000;
    private static final int ATTEMPTS = 3;

    private final Passcode passcode = new RsaPasscode();

    @Test
    public void shouldHaveCorrectLength() {
        assertThat(passcode.getLength()).isEqualTo(LENGTH);
    }

    @Test
    public void shouldHaveCorrectDuration() {
        assertThat(passcode.getDuration()).isEqualTo(DURATION);
    }

    @Test
    public void shouldHaveCorrectAttempts() {
        assertThat(passcode.getAttempts()).isEqualTo(ATTEMPTS);
    }

}
