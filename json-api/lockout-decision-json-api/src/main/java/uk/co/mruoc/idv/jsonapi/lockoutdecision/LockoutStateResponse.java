package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface LockoutStateResponse {

    UUID getId();

    UUID getIdvId();

    Collection<VerificationAttempt> getAttempts();

    Collection<Alias> getAliases();

    boolean isLocked();

    String getType();

    Optional<Integer> getNumberOfAttemptsRemaining();

    Optional<Long> getDuration();

    Optional<Instant> getLockedUntil();

}
