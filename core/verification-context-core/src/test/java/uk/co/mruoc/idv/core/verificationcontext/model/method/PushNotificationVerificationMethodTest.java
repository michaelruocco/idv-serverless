package uk.co.mruoc.idv.core.verificationcontext.model.method;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PushNotificationVerificationMethodTest {

    private static final int DURATION = 300000;

    private final VerificationMethod pushNotification = new PushNotificationVerificationMethod(DURATION);

    @Test
    public void shouldReturnName() {
        final String name = pushNotification.getName();

        assertThat(name).isEqualTo(VerificationMethod.Names.PUSH_NOTIFICATION);
    }

    @Test
    public void shouldReturnDuration() {
        final int duration = pushNotification.getDuration();

        assertThat(duration).isEqualTo(DURATION);
    }

}
