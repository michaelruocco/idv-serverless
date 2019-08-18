package uk.co.mruoc.idv.core.verificationattempts.dao;

import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

public interface VerificationAttemptsDao {

    Optional<VerificationAttempts> loadByIdvId(final UUID idvId);

    Optional<VerificationAttempts> loadByContextId(final UUID contextId);

    void save(final VerificationAttempts attempts);

}
