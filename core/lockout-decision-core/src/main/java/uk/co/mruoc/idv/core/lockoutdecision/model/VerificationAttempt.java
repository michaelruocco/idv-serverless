package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.time.Instant;
import java.util.Optional;

@Getter
@Builder
@ToString
public class VerificationAttempt implements LoadLockoutStateRequest {

    private final String channelId;
    private final Instant timestamp;
    private final Alias alias;
    private final String activityType;
    private final String methodName;
    private final boolean successful;

    public Optional<String> getMethodName() {
        return Optional.ofNullable(methodName);
    }

    public String getAliasTypeName() {
        return alias.getTypeName();
    }

}
