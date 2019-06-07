package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.Collection;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class DefaultLockoutStateResponse implements LockoutStateResponse {

    private final VerificationAttempts attempts;

    @Override
    public UUID getId() {
        return attempts.getLockoutStateId();
    }

    @Override
    public IdvIdAlias getIdvIdAlias() {
        return attempts.getIdvIdAlias();
    }

    @Override
    public VerificationAttempts getVerificationAttempts() {
        return attempts;
    }

    @Override
    public Collection<VerificationAttempt> getAttempts() {
        return attempts.getAttempts();
    }

}
