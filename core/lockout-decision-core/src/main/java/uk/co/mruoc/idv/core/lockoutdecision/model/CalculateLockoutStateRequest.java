package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.time.Instant;

@Builder
@Getter
public class CalculateLockoutStateRequest {

    private final Alias alias;
    private final VerificationAttempts attempts;
    private final Instant timestamp;

}
