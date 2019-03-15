package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;

import java.util.Optional;
import java.util.UUID;

public interface VerificationContextDao {

    void save(final VerificationContext context);

    Optional<VerificationContext> load(final UUID id);

}
