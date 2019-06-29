package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@ToString
public class VerificationAttempt implements LockoutStateRequest {

    private final String channelId;
    private final Instant timestamp;
    private final Alias alias;
    private final String activityType;
    private final String methodName;
    private final UUID verificationId;
    private final boolean successful;

    public String getAliasTypeName() {
        return alias.getTypeName();
    }

}
