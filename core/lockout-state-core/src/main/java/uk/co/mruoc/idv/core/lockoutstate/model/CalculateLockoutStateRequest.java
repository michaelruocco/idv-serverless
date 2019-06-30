package uk.co.mruoc.idv.core.lockoutstate.model;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.time.Instant;

@Builder
@Getter
public class CalculateLockoutStateRequest {

    private final Alias alias;
    private final VerificationAttempts attempts;
    private final Instant timestamp;

}