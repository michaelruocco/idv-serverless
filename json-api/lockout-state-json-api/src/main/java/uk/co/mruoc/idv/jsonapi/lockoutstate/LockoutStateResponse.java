package uk.co.mruoc.idv.jsonapi.lockoutstate;

import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface LockoutStateResponse {

    UUID getId();

    UUID getIdvId();

    Collection<VerificationAttempt> getAttempts();

    boolean isLocked();

    String getType();

    Optional<Integer> getNumberOfAttemptsRemaining();

    Optional<Long> getDuration();

    Optional<Instant> getLockedUntil();

}
