package uk.co.mruoc.idv.core.lockoutdecision.model;

import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.Collection;
import java.util.UUID;

public interface LockoutState {

    UUID getId();

    UUID getIdvId();

    IdvIdAlias getIdvIdAlias();

    String getType();

    boolean isLocked();

    int getNumberOfAttempts();

    VerificationAttempts getVerificationAttempts();

    Collection<VerificationAttempt> getAttempts();

    boolean isTimeBased();

}
