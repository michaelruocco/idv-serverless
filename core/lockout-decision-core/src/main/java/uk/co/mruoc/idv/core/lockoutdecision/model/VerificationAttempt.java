package uk.co.mruoc.idv.core.lockoutdecision.model;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.time.Instant;
import java.util.Optional;

public interface VerificationAttempt {

    String getChannelId();

    Instant getTimestamp();

    String getAliasTypeName();

    Alias getAlias();

    String getActivityType();

    Optional<String> getMethodName();

    boolean isSuccessful();

}
