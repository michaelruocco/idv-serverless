package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Getter
public class VerificationSequencePolicy {

    private final String name;
    private final Collection<VerificationMethodPolicy> methods;

    public VerificationSequencePolicy(final VerificationMethodPolicy method) {
        this(method.getMethodName(), Collections.singleton(method));
    }

    public VerificationSequencePolicy(final String name, final VerificationMethodPolicy... method) {
        this(name, Arrays.asList(method));
    }

    public VerificationSequencePolicy(final String name, final Collection<VerificationMethodPolicy> methods) {
        this.name = name;
        this.methods = methods;
    }

}
