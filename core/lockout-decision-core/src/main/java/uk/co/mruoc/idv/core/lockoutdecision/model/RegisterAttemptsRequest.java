package uk.co.mruoc.idv.core.lockoutdecision.model;

import java.util.Collection;
import java.util.UUID;

public interface RegisterAttemptsRequest {

    UUID getContextId();

    Collection<RegisterAttemptRequest> getAttempts();

}
