package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Builder;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.time.Instant;
import java.util.Collection;

@Builder
public class CreateVerificationContextService {

    private final VerificationContextRequestConverter requestConverter;
    private final IdentityService identityService;
    private final UuidGenerator idGenerator;
    private final TimeService timeService;
    private final ExpiryCalculator expiryCalculator;
    private final VerificationPoliciesService policiesService;
    private final EligibleMethodsService eligibleMethodsService;
    private final VerificationContextDao dao;

    public VerificationContext create(final VerificationContextRequest request) {
        final UpsertIdentityRequest upsertIdentityRequest = requestConverter.toUpsertIdentityRequest(request);
        final Identity identity = identityService.upsert(upsertIdentityRequest);
        final VerificationContext context = buildContext(request, identity);
        dao.save(context);
        return context;
    }

    private VerificationContext buildContext(final VerificationContextRequest request, final Identity identity) {
        final Collection<VerificationMethodSequence> eligibleMethods = loadEligibleMethods(request, identity);
        final Instant created = timeService.now();
        return VerificationContext.builder()
                .id(idGenerator.randomUuid())
                .channel(request.getChannel())
                .providedAlias(request.getProvidedAlias())
                .identity(identity)
                .activity(request.getActivity())
                .created(created)
                .expiry(expiryCalculator.calculateExpiry(created))
                .eligibleMethods(eligibleMethods)
                .build();
    }

    private VerificationPolicy loadVerificationPolicy(final VerificationContextRequest request) {
        final Channel channel = request.getChannel();
        final Activity activity = request.getActivity();
        final ChannelVerificationPolicies policies = policiesService.getPoliciesForChannel(channel.getId());
        return policies.getPolicyFor(activity.getType());
    }

    private Collection<VerificationMethodSequence> loadEligibleMethods(final VerificationContextRequest request, final Identity identity) {
        final VerificationPolicy policy = loadVerificationPolicy(request);
        final EligibleMethodsRequest methodsRequest = EligibleMethodsRequest.builder()
                .channel(request.getChannel())
                .identity(identity)
                .inputAlias(request.getProvidedAlias())
                .policy(policy)
                .build();
        return eligibleMethodsService.loadEligibleMethods(methodsRequest);
    }

}