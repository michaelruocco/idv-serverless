package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class DefaultRegisterAttemptRequest implements RegisterAttemptRequest {

    private final String methodName;
    private final UUID verificationId;
    private final Instant timestamp;
    private final boolean successful;

}
