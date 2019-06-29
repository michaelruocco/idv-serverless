package uk.co.mruoc.idv.core.lockoutdecision.model;

import java.time.Instant;
import java.util.UUID;

public interface RegisterAttemptRequest {

    String getMethodName();

    UUID getVerificationId();

    Instant getTimestamp();

    boolean isSuccessful();

}
