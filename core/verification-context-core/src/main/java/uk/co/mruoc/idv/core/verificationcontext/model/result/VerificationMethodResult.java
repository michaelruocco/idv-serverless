package uk.co.mruoc.idv.core.verificationcontext.model.result;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@ToString
public class VerificationMethodResult {

    private final UUID contextId;
    private final String sequenceName;
    private final String methodName;
    private final UUID verificationId;
    private final boolean successful;
    private final Instant timestamp;

}
