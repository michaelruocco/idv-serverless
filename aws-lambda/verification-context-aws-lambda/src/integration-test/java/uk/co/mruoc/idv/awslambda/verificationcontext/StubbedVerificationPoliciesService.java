package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PushNotificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService;


public class StubbedVerificationPoliciesService implements VerificationPoliciesService {

    private static final int DURATION = 300000;

    @Override
    public ChannelVerificationPolicies getPoliciesForChannel(final String channelId) {
        final VerificationMethodPolicy policy = new PushNotificationMethodPolicy(DURATION);
        final VerificationSequencePolicy entry = new VerificationSequencePolicy(policy);
        return new ChannelVerificationPolicies(channelId, new VerificationPolicy(Activity.Types.LOGIN, entry));
    }

}
