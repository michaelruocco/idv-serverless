package uk.co.mruoc.idv.core.lockoutstate.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import static org.assertj.core.api.Assertions.assertThat;

public class NotLockedTimeBasedLockoutStateTest {

    private final TimeBasedLockoutState state = new NotLockedTimeBasedLockoutState(VerificationAttempts.builder().build(), "");

    @Test
    public void shouldNotBeLocked() {
        assertThat(state.isLocked()).isFalse();
    }

    @Test
    public void shouldReturnEmptyDuration() {
        assertThat(state.getDuration()).isEmpty();
    }

    @Test
    public void shouldReturnEmptyDurationInMillis() {
        assertThat(state.getDurationInMillis()).isEmpty();
    }

    @Test
    public void shouldReturnEmptyLockedUntil() {
        assertThat(state.getLockedUntil()).isEmpty();
    }

}