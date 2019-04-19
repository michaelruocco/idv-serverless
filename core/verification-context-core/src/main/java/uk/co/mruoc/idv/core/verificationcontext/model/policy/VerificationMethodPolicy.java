package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class VerificationMethodPolicy {

    public static final int DEFAULT_DURATION = 300000;
    public static final int DEFAULT_MAX_ATTEMPTS = 1;

    private final String methodName;
    private final int duration;
    private final int maximumAttempts;

    public VerificationMethodPolicy(final String name) {
        this(name, DEFAULT_DURATION);
    }

    public VerificationMethodPolicy(final String name, final int duration) {
        this(name, duration, DEFAULT_MAX_ATTEMPTS);
    }

}
