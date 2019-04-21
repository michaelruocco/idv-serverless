package uk.co.mruoc.idv.core.verificationcontext.service;


import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.util.Optional;
import java.util.UUID;

public interface VerificationResultDao {

    Optional<VerificationMethodResults> load(final UUID verificationContextId);

    void save(VerificationMethodResults results);

}
