package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.lockoutdecision.service.DefaultLockoutPoliciesService;

import java.util.Arrays;
import java.util.Collections;

public class UkLockoutPoliciesService extends DefaultLockoutPoliciesService {

    public UkLockoutPoliciesService() {
        super(Arrays.asList(
                new RsaChannelLockoutPolicies(),
                new As3ChannelLockoutPolicies()
        ));
    }

}
