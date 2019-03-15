package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class VerificationMethodPolicy {

    public static final int DEFAULT_DURATION = 300000;

    private final String methodName;
    private final int duration;

    public VerificationMethodPolicy(final String name) {
        this(name, DEFAULT_DURATION);
    }

}
