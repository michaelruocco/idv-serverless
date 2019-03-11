package uk.co.mruoc.idv.core.verificationcontext.model.activity;

import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginActivityTest {

    private static final Instant TIMESTAMP = Instant.now();

    private final Activity activity = new LoginActivity(TIMESTAMP);

    @Test
    public void shouldReturnType() {
        final String type = activity.getType();

        assertThat(type).isEqualTo(Activity.Types.LOGIN);
    }

    @Test
    public void shouldReturnTimestamp() {
        final Instant timestamp = activity.getTimestamp();

        assertThat(timestamp).isEqualTo(TIMESTAMP);
    }

}
