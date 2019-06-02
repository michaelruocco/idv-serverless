package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService.LockoutStateIsLockedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LockoutStateIsLockedExceptionTest {

    private final LockoutState state = mock(LockoutState.class);

    @Test
    public void shouldReturnLockoutState() {
        final LockoutStateIsLockedException exception = new LockoutStateIsLockedException(state);

        assertThat(exception.getLockoutState()).isEqualTo(state);
    }

    @Test
    public void shouldReturnMessage() {
        final Throwable exception = new LockoutStateIsLockedException(state);

        assertThat(exception.getMessage()).startsWith("cannot create verification context lockout state is locked");
    }

}
