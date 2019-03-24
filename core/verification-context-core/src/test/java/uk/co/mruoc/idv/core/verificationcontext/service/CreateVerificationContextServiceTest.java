package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies.VerificationPolicyNotConfiguredForActivityException;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PushNotificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService.VerificationPolicyNotConfiguredForChannelException;

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

public class CreateVerificationContextServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final Duration FIVE_MINUTES = Duration.ofMinutes(5);

    private final VerificationContextRequestConverter requestConverter = mock(VerificationContextRequestConverter.class);
    private final IdentityService identityService = mock(IdentityService.class);
    private final UuidGenerator idGenerator = mock(UuidGenerator.class);
    private final TimeService timeService = mock(TimeService.class);
    private final ExpiryCalculator expiryCalculator = mock(ExpiryCalculator.class);
    private final DefaultVerificationPoliciesService policiesService = mock(DefaultVerificationPoliciesService.class);
    private final DefaultEligibleMethodsService eligibleMethodsService = mock(DefaultEligibleMethodsService.class);
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final CreateVerificationContextService service = CreateVerificationContextService.builder()
            .requestConverter(requestConverter)
            .identityService(identityService)
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
        doThrow(VerificationPolicyNotConfiguredForChannelException.class).when(policiesService).getPoliciesForChannel(channel.getId());

        final Throwable thrown = catchThrowable(() -> service.create(request));

        assertThat(thrown).isInstanceOf(VerificationPolicyNotConfiguredForChannelException.class);
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

        final UpsertIdentityRequest upsertIdentityRequest = mock(UpsertIdentityRequest.class);
        given(requestConverter.toUpsertIdentityRequest(request)).willReturn(upsertIdentityRequest);

        final Identity identity = mock(Identity.class);
        given(identityService.upsert(upsertIdentityRequest)).willReturn(identity);

        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);

        final UUID contextId = UUID.randomUUID();
        given(idGenerator.randomUuid()).willReturn(contextId);

        final Instant expiry = now.plus(FIVE_MINUTES);
        given(expiryCalculator.calculateExpiry(now)).willReturn(expiry);

        final ChannelVerificationPolicies channelPolicies = buildChannelPolicies();
        final Channel channel = request.getChannel();
        given(policiesService.getPoliciesForChannel(channel.getId())).willReturn(channelPolicies);

        final Collection<VerificationMethodSequence> eligibleMethods = Collections.singleton(mock(VerificationMethodSequence.class));
        given(eligibleMethodsService.loadEligibleMethods(any(EligibleMethodsRequest.class))).willReturn(eligibleMethods);

        final VerificationContext context = service.create(request);

        verify(dao).save(context);
        assertThat(context.getId()).isEqualTo(contextId);
        assertThat(context.getChannel()).isEqualTo(request.getChannel());
        assertThat(context.getProvidedAlias()).isEqualTo(request.getProvidedAlias());
        assertThat(context.getIdentity()).isEqualTo(identity);
        assertThat(context.getActivity()).isEqualTo(request.getActivity());
        assertThat(context.getCreated()).isEqualTo(now);
        assertThat(context.getExpiry()).isEqualTo(expiry);
        assertThat(context.getEligibleMethods()).isEqualTo(eligibleMethods);
    }

    @Test
    public void shouldPassCorrectRequestToEligibleMethodsService() {
        final VerificationContextRequest request = buildRequest();

        final UpsertIdentityRequest upsertIdentityRequest = mock(UpsertIdentityRequest.class);
        given(requestConverter.toUpsertIdentityRequest(request)).willReturn(upsertIdentityRequest);

        final Identity identity = mock(Identity.class);
        given(identityService.upsert(upsertIdentityRequest)).willReturn(identity);

        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);

        final UUID contextId = UUID.randomUUID();
        given(idGenerator.randomUuid()).willReturn(contextId);

        final Instant expiry = now.plus(FIVE_MINUTES);
        given(expiryCalculator.calculateExpiry(now)).willReturn(expiry);

        final ChannelVerificationPolicies channelPolicies = buildChannelPolicies();
        final Channel channel = request.getChannel();
        given(policiesService.getPoliciesForChannel(channel.getId())).willReturn(channelPolicies);

        final Collection<VerificationMethodSequence> eligibleMethods = Collections.singleton(mock(VerificationMethodSequence.class));
        given(eligibleMethodsService.loadEligibleMethods(any(EligibleMethodsRequest.class))).willReturn(eligibleMethods);

        service.create(request);

        final ArgumentCaptor<EligibleMethodsRequest> captor = ArgumentCaptor.forClass(EligibleMethodsRequest.class);

        verify(eligibleMethodsService).loadEligibleMethods(captor.capture());
        final EligibleMethodsRequest methodsRequest = captor.getValue();
        assertThat(methodsRequest.getChannel()).isEqualTo(request.getChannel());
        assertThat(methodsRequest.getInputAlias()).isEqualTo(request.getProvidedAlias());
        assertThat(methodsRequest.getIdentity()).isEqualTo(identity);
        assertThat(methodsRequest.getPolicy()).isEqualTo(channelPolicies.getPolicyFor(request.getActivity().getType()));
    }

    private static VerificationContextRequest buildRequest() {
        final VerificationContextRequest request = mock(VerificationContextRequest.class);
        given(request.getChannel()).willReturn(new DefaultChannel(CHANNEL_ID));
        given(request.getProvidedAlias()).willReturn(new IdvIdAlias());
        given(request.getActivity()).willReturn(new LoginActivity(Instant.now()));
        return request;
    }

    private static ChannelVerificationPolicies buildChannelPolicies() {
        final VerificationMethodPolicyEntry entry = new VerificationMethodPolicyEntry(new PushNotificationMethodPolicy());
        final Collection<VerificationPolicy> policies = Collections.singleton(new VerificationPolicy(Activity.Types.LOGIN, Collections.singleton(entry)));
        return new ChannelVerificationPolicies(CHANNEL_ID, policies);
    }

}
