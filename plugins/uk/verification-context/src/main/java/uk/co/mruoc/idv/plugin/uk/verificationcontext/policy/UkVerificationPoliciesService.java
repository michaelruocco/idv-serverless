package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy;

import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3.As3ChannelVerificationPolicies;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.bbos.BbosChannelVerificationPolicies;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa.RsaChannelVerificationPolicies;

import java.util.Arrays;

public class UkVerificationPoliciesService extends VerificationPoliciesService {

    public UkVerificationPoliciesService() {
        super(Arrays.asList(
                new RsaChannelVerificationPolicies(),
                new As3ChannelVerificationPolicies(),
                new BbosChannelVerificationPolicies()
        ));
    }

}
