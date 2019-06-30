package uk.co.mruoc.idv.core.lockoutstate.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NonLockingLockoutStateTest {

    @Test
    public void shouldReturnMaxAttemptsType() {
        final LockoutState state = NonLockingLockoutState.builder()
                .build();

        assertThat(state.getType()).isEqualTo(LockoutType.NON_LOCKING);
    }

    @Test
    public void shouldAlwaysReturnNotLocked() {
        final LockoutState state = NonLockingLockoutState.builder()
                .build();

        assertThat(state.isLocked()).isFalse();
    }

}
