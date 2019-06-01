package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Getter
public class VerificationSequencePolicy {

    private static final RegisterAttemptStrategy DEFAULT_FAILURE_STRATEGY = RegisterAttemptStrategy.IMMEDIATE;

    private final String name;
    private final RegisterAttemptStrategy registerAttemptStrategy;
    private final Collection<VerificationMethodPolicy> methods;

    public VerificationSequencePolicy(final VerificationMethodPolicy method) {
        this(DEFAULT_FAILURE_STRATEGY, method);
    }

    public VerificationSequencePolicy(final RegisterAttemptStrategy registerAttemptStrategy, final VerificationMethodPolicy method) {
        this(method.getMethodName(), registerAttemptStrategy, Collections.singleton(method));
    }

    public VerificationSequencePolicy(final String name, final VerificationMethodPolicy... method) {
        this(name, DEFAULT_FAILURE_STRATEGY, Arrays.asList(method));
    }

    public VerificationSequencePolicy(final String name, final RegisterAttemptStrategy registerAttemptStrategy, final VerificationMethodPolicy... method) {
        this(name, registerAttemptStrategy, Arrays.asList(method));
    }

    public VerificationSequencePolicy(final String name, final RegisterAttemptStrategy registerAttemptStrategy, final Collection<VerificationMethodPolicy> methods) {
        this.name = name;
        this.registerAttemptStrategy = registerAttemptStrategy;
        this.methods = methods;
    }

}
