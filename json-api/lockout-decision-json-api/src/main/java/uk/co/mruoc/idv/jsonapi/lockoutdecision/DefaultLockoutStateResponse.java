package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class DefaultLockoutStateResponse implements LockoutStateResponse {

    private final LockoutState state;

    @Override
    public UUID getId() {
        return state.getId();
    }

    @Override
    public UUID getIdvId() {
        return state.getIdvId();
    }

    @Override
    public Collection<Alias> getAliases() {
        final Aliases aliases = state.getAliases();
        return aliases.toCollection();
    }

    @Override
    public boolean isLocked() {
        return state.isLocked();
    }

    @Override
    public String getType() {
        return state.getType();
    }

    @Override
    public Optional<Integer> getNumberOfAttemptsRemaining() {
        return LockoutState.extractNumberOfAttemptsRemaining(state);
    }

    @Override
    public Optional<Long> getDuration() {
        return LockoutState.extractDuration(state);
    }

    @Override
    public Optional<Instant> getLockedUntil() {
        return LockoutState.extractLockedUntil(state);
    }

    @Override
    public Collection<VerificationAttempt> getAttempts() {
        final VerificationAttempts attempts = state.getAttempts();
        return attempts.toCollection();
    }



}
