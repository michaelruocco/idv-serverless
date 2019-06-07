package uk.co.mruoc.idv.core.lockoutdecision.model;

import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.Collection;
import java.util.UUID;

public interface LockoutStateResponse {

    UUID getId();

    IdvIdAlias getIdvIdAlias();

    Collection<VerificationAttempt> getAttempts();

    VerificationAttempts getVerificationAttempts();

}
