package uk.co.mruoc.idv.core.verificationattempts.model;

import java.util.Collection;

public interface RegisterAttemptsRequest {

    Collection<RegisterAttemptRequest> getAttempts();

}
