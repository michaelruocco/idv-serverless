package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Getter
public class VerificationMethodPolicyEntry {

    private final String name;
    private final Collection<VerificationMethodPolicy> methods;

    public VerificationMethodPolicyEntry(final VerificationMethodPolicy method) {
        this(method.getMethodName(), Collections.singleton(method));
    }

    public VerificationMethodPolicyEntry(final String name, final VerificationMethodPolicy... method) {
        this(name, Arrays.asList(method));
    }

    public VerificationMethodPolicyEntry(final String name, final Collection<VerificationMethodPolicy> methods) {
        this.name = name;
        this.methods = methods;
    }

}
