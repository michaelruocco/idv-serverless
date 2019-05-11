package uk.co.mruoc.idv.core.lockoutdecision.model;

import java.time.Instant;
import java.util.Optional;

public interface VerificationAttempt {

    Instant getTimestamp();

    String getAliasTypeName();

    String getActivityType();

    Optional<String> getMethodName();

}
