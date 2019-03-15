package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.As3Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.as3.As3ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies.VerificationPolicyNotConfiguredForActivityException;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService.UnrecognisedChannelException;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VerificationContextServiceTest {

    private static final Duration FIVE_MINUTES = Duration.ofMinutes(5);

    private final UuidGenerator idGenerator = mock(UuidGenerator.class);
    private final TimeService timeService = mock(TimeService.class);
    private final ExpiryCalculator expiryCalculator = mock(ExpiryCalculator.class);
    private final VerificationPoliciesService policiesService = mock(VerificationPoliciesService.class);
    private final EligibleMethodsService eligibleMethodsService = mock(EligibleMethodsService.class);
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final VerificationContextService service = VerificationContextService.builder()
            .idGenerator(idGenerator)
            .timeService(timeService)
            .expiryCalculator(expiryCalculator)
            .policiesService(policiesService)
            .eligibleMethodsService(eligibleMethodsService)
            .dao(dao)
            .build();

    @Test
    public void shouldThrowExceptionIfVerificationPolicyNotConfiguredForChannel() {
        final VerificationContextRequest request = buildRequest();
        final Channel channel = request.getChannel();
        doThrow(UnrecognisedChannelException.class).when(policiesService).getPoliciesForChannel(channel.getId());

        final Throwable thrown = catchThrowable(() -> service.create(request));

        assertThat(thrown).isInstanceOf(UnrecognisedChannelException.class);
    }

    @Test
    public void shouldThrowExceptionIfVerificationPolicyNotConfiguredForActivity() {
        final VerificationContextRequest request = buildRequest();
        final Channel channel = request.getChannel();
        final Activity activity = request.getActivity();
        final ChannelVerificationPolicies policies = mock(ChannelVerificationPolicies.class);
        given(policiesService.getPoliciesForChannel(channel.getId())).willReturn(policies);
        doThrow(VerificationPolicyNotConfiguredForActivityException.class).when(policies).getPolicyFor(activity.getType());

        final Throwable thrown = catchThrowable(() -> service.create(request));

        assertThat(thrown).isInstanceOf(VerificationPolicyNotConfiguredForActivityException.class);
    }

    @Test
    public void shouldCreateVerificationContext() {
        final VerificationContextRequest request = buildRequest();

        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);

        final UUID contextId = UUID.randomUUID();
        given(idGenerator.randomUuid()).willReturn(contextId);

        final Instant expiry = now.plus(FIVE_MINUTES);
        given(expiryCalculator.calculateExpiry(now)).willReturn(expiry);

        final ChannelVerificationPolicies channelPolicies = new As3ChannelVerificationPolicies();
        final Channel channel = request.getChannel();
        given(policiesService.getPoliciesForChannel(channel.getId())).willReturn(channelPolicies);

        final Collection<VerificationMethodSequence> eligibleMethods = Collections.singleton(mock(VerificationMethodSequence.class));
        given(eligibleMethodsService.loadEligibleMethods(any(EligibleMethodsRequest.class))).willReturn(eligibleMethods);

        final VerificationContext context = service.create(request);

        assertThat(context.getId()).isEqualTo(contextId);
        assertThat(context.getChannel()).isEqualTo(request.getChannel());
        assertThat(context.getInputAlias()).isEqualTo(request.getInputAlias());
        assertThat(context.getIdentity()).isEqualTo(request.getIdentity());
        assertThat(context.getActivity()).isEqualTo(request.getActivity());
        assertThat(context.getCreated()).isEqualTo(now);
        assertThat(context.getExpiry()).isEqualTo(expiry);
        assertThat(context.getEligibleMethods()).isEqualTo(eligibleMethods);
    }

    @Test
    public void shouldPassCorrectRequestToEligibleMethodsService() {
        final VerificationContextRequest request = buildRequest();

        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);

        final UUID contextId = UUID.randomUUID();
        given(idGenerator.randomUuid()).willReturn(contextId);

        final Instant expiry = now.plus(FIVE_MINUTES);
        given(expiryCalculator.calculateExpiry(now)).willReturn(expiry);

        final ChannelVerificationPolicies channelPolicies = new As3ChannelVerificationPolicies();
        final Channel channel = request.getChannel();
        given(policiesService.getPoliciesForChannel(channel.getId())).willReturn(channelPolicies);

        final Collection<VerificationMethodSequence> eligibleMethods = Collections.singleton(mock(VerificationMethodSequence.class));
        given(eligibleMethodsService.loadEligibleMethods(any(EligibleMethodsRequest.class))).willReturn(eligibleMethods);

        final VerificationContext context = service.create(request);

        final ArgumentCaptor<EligibleMethodsRequest> captor = ArgumentCaptor.forClass(EligibleMethodsRequest.class);

        verify(eligibleMethodsService).loadEligibleMethods(captor.capture());
        final EligibleMethodsRequest methodsRequest = captor.getValue();
        assertThat(methodsRequest.getChannel()).isEqualTo(request.getChannel());
        assertThat(methodsRequest.getInputAlias()).isEqualTo(request.getInputAlias());
        assertThat(methodsRequest.getIdentity()).isEqualTo(request.getIdentity());
        assertThat(methodsRequest.getPolicy()).isEqualTo(channelPolicies.getPolicyFor(request.getActivity().getType()));
    }

    private static VerificationContextRequest buildRequest() {
        final Instant now = Instant.now();
        final Alias inputAlias = new IdvIdAlias();
        final Channel channel = new As3Channel();
        final Identity identity = Identity.withAliases(inputAlias);
        final Activity activity = new LoginActivity(now);
        return VerificationContextRequest.builder()
                .channel(channel)
                .inputAlias(inputAlias)
                .identity(identity)
                .activity(activity)
                .build();
    }

}
