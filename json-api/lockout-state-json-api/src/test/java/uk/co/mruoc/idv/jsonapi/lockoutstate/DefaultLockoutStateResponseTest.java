package uk.co.mruoc.idv.jsonapi.lockoutstate;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.MaxAttemptsLockoutState;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultLockoutStateResponseTest {

    private final LockoutState state =  mock(LockoutState.class);

    @Test
    public void shouldReturnId() {
        final UUID id = UUID.randomUUID();
        given(state.getId()).willReturn(id);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .state(state)
                .build();

        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    public void shouldReturnIdvId() {
        final UUID idvId = UUID.randomUUID();
        given(state.getIdvId()).willReturn(idvId);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .state(state)
                .build();

        assertThat(request.getIdvId()).isEqualTo(idvId);
    }

    @Test
    public void shouldReturnIsLocked() {
        final boolean locked = true;
        given(state.isLocked()).willReturn(locked);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .state(state)
                .build();

        assertThat(request.isLocked()).isEqualTo(locked);
    }

    @Test
    public void shouldReturnType() {
        final String type = "type";
        given(state.getType()).willReturn(type);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .state(state)
                .build();

        assertThat(request.getType()).isEqualTo(type);
    }

    @Test
    public void shouldReturnEmptyOptionalIfStateIsNotMaxAttempts() {
        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .state(state)
                .build();

        assertThat(request.getNumberOfAttemptsRemaining()).isEmpty();
    }

    @Test
    public void shouldReturnAttemptsRemainingIfStateIsNotMaxAttempts() {
        final int attemptsRemaining = 3;
        final MaxAttemptsLockoutState maxAttemptsState = mock(MaxAttemptsLockoutState.class);
        given(maxAttemptsState.isMaxAttempts()).willReturn(true);
        given(maxAttemptsState.getNumberOfAttemptsRemaining()).willReturn(attemptsRemaining);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .state(maxAttemptsState)
                .build();

        assertThat(request.getNumberOfAttemptsRemaining()).contains(attemptsRemaining);
    }

    @Test
    public void shouldReturnVerificationAttemptsAsCollection() {
        final VerificationAttempts attempts = VerificationAttempts.builder().build();
        given(state.getAttempts()).willReturn(attempts);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .state(state)
                .build();

        assertThat(request.getAttempts()).containsExactlyElementsOf(attempts);
    }

    @Test
    public void shouldHaveNoArgsConstructorForJackson() {
        final LockoutStateResponse response = new DefaultLockoutStateResponse();

        assertThat(response).isNotNull();
    }

}