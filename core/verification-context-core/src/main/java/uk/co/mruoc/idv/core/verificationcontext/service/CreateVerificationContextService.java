package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Builder;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.channel.model.Channel;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.AbstractVerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.MethodSequencesRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;
import uk.co.mruoc.idv.events.EventPublisher;

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
    private final VerificationMethodsService verificationMethodsService;
    private final VerificationContextDao dao;
    private final VerificationContextConverter contextConverter;
    private final EventPublisher eventPublisher;
    private final LockoutStateService lockoutStateService;

    public VerificationContext create(final AbstractVerificationContextRequest request) {
        final UpsertIdentityRequest upsertIdentityRequest = requestConverter.toUpsertIdentityRequest(request);
        final Identity identity = identityService.upsert(upsertIdentityRequest);
        validateLockoutState(request);
        final VerificationContext context = buildContext(request, identity);
        dao.save(context);
        eventPublisher.publish(contextConverter.toCreatedEvent(context));
        return context;
    }

    private void validateLockoutState(final AbstractVerificationContextRequest request) {
        final LockoutState state = lockoutStateService.load(request);
        if (state.isLocked()) {
            throw new LockoutStateIsLockedException(state);
        }
    }

    private VerificationContext buildContext(final VerificationContextRequest request, final Identity identity) {
        final Collection<VerificationMethodSequence> sequences = loadMethodSequences(request, identity);
        final Instant created = timeService.now();
        return VerificationContext.builder()
                .id(idGenerator.randomUuid())
                .channel(request.getChannel())
                .providedAlias(request.getProvidedAlias())
                .identity(identity)
                .activity(request.getActivity())
                .created(created)
                .expiry(expiryCalculator.calculateExpiry(created))
                .sequences(sequences)
                .build();
    }

    private VerificationPolicy loadVerificationPolicy(final VerificationContextRequest request) {
        final Channel channel = request.getChannel();
        final Activity activity = request.getActivity();
        final ChannelVerificationPolicies policies = policiesService.getPoliciesForChannel(channel.getId());
        return policies.getPolicyFor(activity.getType());
    }

    private Collection<VerificationMethodSequence> loadMethodSequences(final VerificationContextRequest request, final Identity identity) {
        final VerificationPolicy policy = loadVerificationPolicy(request);
        final MethodSequencesRequest methodsRequest = MethodSequencesRequest.builder()
                .channel(request.getChannel())
                .identity(identity)
                .inputAlias(request.getProvidedAlias())
                .policy(policy)
                .build();
        return verificationMethodsService.loadMethodSequences(methodsRequest);
    }

    public static class LockoutStateIsLockedException extends RuntimeException {

        private final LockoutState state;

        public LockoutStateIsLockedException(final LockoutState state) {
            super(String.format("cannot create verification context lockout state is locked %s", state));
            this.state = state;
        }

        public LockoutState getLockoutState() {
            return state;
        }

    }

}
