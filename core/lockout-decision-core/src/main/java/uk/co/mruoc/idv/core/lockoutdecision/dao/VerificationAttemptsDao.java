package uk.co.mruoc.idv.core.lockoutdecision.dao;

import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.UUID;

public interface VerificationAttemptsDao {

    VerificationAttempts loadByIdvId(final UUID idvId);

    void save(final VerificationAttempts attempts);

}
