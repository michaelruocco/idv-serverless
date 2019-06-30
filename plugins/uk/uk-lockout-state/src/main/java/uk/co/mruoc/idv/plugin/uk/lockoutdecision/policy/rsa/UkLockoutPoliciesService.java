package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.lockoutstate.service.DefaultLockoutPoliciesService;

import java.util.Arrays;

public class UkLockoutPoliciesService extends DefaultLockoutPoliciesService {

    public UkLockoutPoliciesService() {
        super(Arrays.asList(
                new RsaChannelLockoutPolicies(),
                new As3ChannelLockoutPolicies()
        ));
    }

}
