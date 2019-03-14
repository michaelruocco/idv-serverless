package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerificationMethodPolicy {

    public static final int DEFAULT_DURATION = 300000;

    private final String methodName;
    private final int duration;

    public VerificationMethodPolicy(final String name) {
        this(name, DEFAULT_DURATION);
    }

}
