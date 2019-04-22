package uk.co.mruoc.idv.core.verificationcontext.model.result;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class VerificationMethodResult {

    private final UUID contextId;
    private final String sequenceName;
    private final String methodName;
    private final UUID verificationId;
    private final VerificationResult result;
    private final Instant timestamp;

    public boolean isSuccessful() {
        return VerificationResult.SUCCESS.equals(result);
    }

}
