package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultLockoutStateResponseTest {

    @Test
    public void shouldReturnId() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        final UUID id = UUID.randomUUID();
        given(attempts.getLockoutStateId()).willReturn(id);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    public void shouldReturnIdvIdAlias() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        final IdvIdAlias idvIdAlias = mock(IdvIdAlias.class);
        given(attempts.getIdvIdAlias()).willReturn(idvIdAlias);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getIdvIdAlias()).isEqualTo(idvIdAlias);
    }

    @Test
    public void shouldReturnVerificationAttempts() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getVerificationAttempts()).isEqualTo(attempts);
    }

    @Test
    public void shouldReturnAttempts() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        final Collection<VerificationAttempt> attemptCollection = Collections.emptyList();
        given(attempts.getAttempts()).willReturn(attemptCollection);

        final LockoutStateResponse request = DefaultLockoutStateResponse.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getAttempts()).isEqualTo(attemptCollection);
    }

    @Test
    public void shouldHaveNoArgsConstructorForJackson() {
        final LockoutStateResponse response = new DefaultLockoutStateResponse();

        assertThat(response).isNotNull();
    }

}