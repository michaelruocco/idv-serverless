package uk.co.mruoc.idv.core.verificationattempts.model;

import java.time.Instant;
import java.util.UUID;

public interface RegisterAttemptRequest {

    UUID getContextId();

    String getMethodName();

    UUID getVerificationId();

    Instant getTimestamp();

    boolean isSuccessful();

}
