package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Builder;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.time.Instant;

@Builder
public class VerificationContextService {

    private final UuidGenerator idGenerator;
    private final TimeService timeService;
    private final ExpiryCalculator expiryCalculator;
    private final VerificationPoliciesService policiesService;
    private final VerificationContextDao dao;

    public VerificationContext create(final VerificationContextRequest request) {
        final VerificationPolicy policy = loadVerificationPolicy(request);
        //TODO add eligibility service, pass policy to eligibility service
        //that will return verification methods to be added to context
        final Instant created = timeService.now();
        return VerificationContext.builder()
                .id(idGenerator.randomUuid())
                .channel(request.getChannel())
                .inputAlias(request.getInputAlias())
                .identity(request.getIdentity())
                .activity(request.getActivity())
                .created(created)
                .expiry(expiryCalculator.calculateExpiry(created))
                .build();
    }

    private VerificationPolicy loadVerificationPolicy(final VerificationContextRequest request) {
        final Channel channel = request.getChannel();
        final Activity activity = request.getActivity();
        final ChannelVerificationPolicies policies = policiesService.getPoliciesForChannel(channel.getId());
        return policies.getPolicyFor(activity.getType());
    }

}
