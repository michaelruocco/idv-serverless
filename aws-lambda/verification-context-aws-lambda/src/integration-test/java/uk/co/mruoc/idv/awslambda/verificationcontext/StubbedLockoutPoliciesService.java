package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.NonLockingLockoutStateCalculator;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutPoliciesService;

import java.util.Collection;
import java.util.Collections;


public class StubbedLockoutPoliciesService implements LockoutPoliciesService {

    @Override
    public ChannelLockoutPolicies getPoliciesForChannel(final String channelId) {
        final Collection<String> all = Collections.singleton("ALL");
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .stateCalculator(new NonLockingLockoutStateCalculator())
                .activities(all)
                .aliasTypes(all)
                .methods(all)
                .build();
        return new ChannelLockoutPolicies(channelId, policy);
    }

}
