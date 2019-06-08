package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultLockoutStateTest {

    private final VerificationAttempts attempts = mock(VerificationAttempts.class);
    private final String lockoutType = LockoutType.MAX_ATTEMPTS;
    private final boolean locked = true;

    private final LockoutState state = new DefaultLockoutState(attempts, lockoutType, locked);

    @Test
    public void shouldReturnId() {
        final UUID lockoutStateId = UUID.randomUUID();
        given(attempts.getLockoutStateId()).willReturn(lockoutStateId);

        final UUID id = state.getId();

        assertThat(id).isEqualTo(lockoutStateId);
    }

    @Test
    public void shouldReturnIdvId() {
        final UUID expectedIdvId = UUID.randomUUID();
        given(attempts.getIdvId()).willReturn(expectedIdvId);

        final UUID idvId = state.getIdvId();

        assertThat(idvId).isEqualTo(expectedIdvId);
    }

    @Test
    public void shouldReturnLockoutType() {
        final String actualLockoutType = state.getType();

        assertThat(actualLockoutType).isEqualTo(lockoutType);
    }

    @Test
    public void shouldReturnLocked() {
        final boolean actualLocked = state.isLocked();

        assertThat(actualLocked).isEqualTo(locked);
    }

    @Test
    public void shouldReturnAttempts() {
        final VerificationAttempts actualAttempts = state.getAttempts();

        assertThat(actualAttempts).isEqualTo(attempts);
    }

    @Test
    public void shouldReturnAliases() {
        final Aliases expectedAliases = Aliases.empty();
        given(attempts.getAliases()).willReturn(expectedAliases);

        final Aliases aliases = state.getAliases();

        assertThat(aliases).isEqualTo(expectedAliases);
    }

    @Test
    public void shouldReturnNumberOfAttempts() {
        final int expectedNumberOfAttempts = 3;
        given(attempts.size()).willReturn(expectedNumberOfAttempts);

        final int numberOfAttempts = state.getNumberOfAttempts();

        assertThat(numberOfAttempts).isEqualTo(expectedNumberOfAttempts);
    }

    @Test
    public void shouldReturnIsTimeBased() {
        final LockoutState timeBasedState = new DefaultLockoutState(attempts, LockoutType.TIME_BASED_RECURRING, locked);
        assertThat(timeBasedState.isTimeBased()).isTrue();

        final LockoutState maxAttemptsState = new DefaultLockoutState(attempts, LockoutType.MAX_ATTEMPTS, locked);
        assertThat(maxAttemptsState.isTimeBased()).isFalse();
    }

}
