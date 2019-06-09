package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.NonLockingLockoutStateCalculator;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutPoliciesService;

import java.util.Collections;


public class StubbedLockoutPoliciesService implements LockoutPoliciesService {

    private static final String ALL = "ALL";

    @Override
    public ChannelLockoutPolicies getPoliciesForChannel(final String channelId) {
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .stateCalculator(new NonLockingLockoutStateCalculator())
                .activities(Collections.singleton(ALL))
                .aliasType(ALL)
                .build();
        return new ChannelLockoutPolicies(channelId, policy);
    }

}
