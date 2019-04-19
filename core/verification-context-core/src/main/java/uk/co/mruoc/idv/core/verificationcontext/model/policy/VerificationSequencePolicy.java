package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Getter
public class VerificationSequencePolicy {

    private static final FailureStrategy DEFAULT_FAILURE_STRATEGY = FailureStrategy.IMMEDIATE;

    private final String name;
    private final FailureStrategy failureStrategy;
    private final Collection<VerificationMethodPolicy> methods;

    public VerificationSequencePolicy(final VerificationMethodPolicy method) {
        this(DEFAULT_FAILURE_STRATEGY, method);
    }

    public VerificationSequencePolicy(final FailureStrategy failureStrategy, final VerificationMethodPolicy method) {
        this(method.getMethodName(), failureStrategy, Collections.singleton(method));
    }

    public VerificationSequencePolicy(final String name, final VerificationMethodPolicy... method) {
        this(name, DEFAULT_FAILURE_STRATEGY, Arrays.asList(method));
    }

    public VerificationSequencePolicy(final String name, final FailureStrategy failureStrategy, final VerificationMethodPolicy... method) {
        this(name, failureStrategy, Arrays.asList(method));
    }

    public VerificationSequencePolicy(final String name, final FailureStrategy failureStrategy, final Collection<VerificationMethodPolicy> methods) {
        this.name = name;
        this.failureStrategy = failureStrategy;
        this.methods = methods;
    }

}
