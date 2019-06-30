package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.lockoutstate.model.DefaultLockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.model.NonLockingLockoutStateCalculator;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutPoliciesService;

import java.util.Collections;

public class StubbedLockoutPoliciesService implements LockoutPoliciesService {

    private static final String ALL = "ALL";

    @Override
    public LockoutPolicy getPolicy(final LockoutStateRequest request) {
        return DefaultLockoutPolicy.builder()
                .stateCalculator(new NonLockingLockoutStateCalculator())
                .activities(Collections.singleton(ALL))
                .aliasTypeName(ALL)
                .build();
    }

}
