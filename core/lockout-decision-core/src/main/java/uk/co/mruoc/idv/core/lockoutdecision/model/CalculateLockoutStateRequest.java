package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class CalculateLockoutStateRequest {

    private final VerificationAttempts attempts;
    private final Instant timestamp;

}
