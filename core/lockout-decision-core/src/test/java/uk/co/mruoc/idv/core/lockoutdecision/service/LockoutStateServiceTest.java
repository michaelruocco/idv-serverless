package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LockoutStateServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final VerificationAttemptsConverter converter = mock(VerificationAttemptsConverter.class);
    private final LockoutPoliciesService policiesService = mock(LockoutPoliciesService.class);
    private final LoadVerificationAttemptsService loadAttemptsService = mock(LoadVerificationAttemptsService.class);
    private final VerificationAttemptsDao dao = mock(VerificationAttemptsDao.class);

    private final LockoutStateService lockoutStateService = LockoutStateService.builder()
            .converter(converter)
            .policiesService(policiesService)
            .loadAttemptsService(loadAttemptsService)
            .dao(dao)
            .build();

    @Test
    public void shouldReturnNullStateIfEmptyCollectionPassed() {
        final Collection<VerificationAttempt> attempts = Collections.emptyList();

        final Throwable cause = catchThrowable(() -> lockoutStateService.register(attempts));

        assertThat(cause).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("attempts collection must not be empty");
    }

    @Test
    public void shouldLoadLockoutStateForAttempt() {
        final VerificationAttempt attempt = buildAttempt();
        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);
        given(lockoutPolicy.appliesToAllAliases()).willReturn(true);
        given(channelPolicies.getPolicyFor(attempt)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt.getAlias())).willReturn(attempts);

        final LockoutState expectedLockoutState = buildLockedState();
        final CalculateLockoutStateRequest request = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(attempts)).willReturn(request);
        given(lockoutPolicy.calculateLockoutState(request)).willReturn(expectedLockoutState);

        final LockoutState lockoutState = lockoutStateService.load(attempt);

        assertThat(lockoutState).isEqualTo(expectedLockoutState);
    }

    @Test
    public void shouldReturnInitialLockoutStateIfInitialStateIsAlreadyLocked() {
        final VerificationAttempt attempt = buildAttempt();
        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);
        given(lockoutPolicy.appliesToAllAliases()).willReturn(true);
        given(channelPolicies.getPolicyFor(attempt)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt.getAlias())).willReturn(attempts);

        final LockoutState initialState = buildLockedState();
        final CalculateLockoutStateRequest request = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(attempts)).willReturn(request);
        given(lockoutPolicy.calculateLockoutState(request)).willReturn(initialState);

        final LockoutState lockoutState = lockoutStateService.register(attempt);

        assertThat(lockoutState).isEqualTo(initialState);
    }

    @Test
    public void shouldResetLockoutStateIfAttemptIsSuccessfulAndInitialStateIsNotLocked() {
        final VerificationAttempt attempt = buildSuccessfulAttempt();
        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);

        given(channelPolicies.getPolicyFor(attempt)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt.getAlias())).willReturn(attempts);

        final VerificationAttempts resetAttempts = mock(VerificationAttempts.class);
        given(lockoutPolicy.reset(attempts)).willReturn(resetAttempts);
        given(lockoutPolicy.appliesToAllAliases()).willReturn(true);

        final LockoutState initialState = buildNotLockedState();
        final CalculateLockoutStateRequest initialRequest = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(attempts)).willReturn(initialRequest);
        given(lockoutPolicy.calculateLockoutState(initialRequest)).willReturn(initialState);

        final LockoutState resetState = buildNotLockedState();
        final CalculateLockoutStateRequest resetRequest = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(resetAttempts)).willReturn(resetRequest);
        given(lockoutPolicy.calculateLockoutState(resetRequest)).willReturn(resetState);

        final LockoutState lockoutState = lockoutStateService.register(attempt);

        assertThat(lockoutState).isEqualTo(resetState);
        verify(dao).save(resetAttempts);
    }

    @Test
    public void shouldUpdateLockoutStateIfAttemptIsUnsuccessfulAndInitialStateIsNotLocked() {
        final VerificationAttempt attempt = buildAttempt();
        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);
        given(lockoutPolicy.appliesToAllAliases()).willReturn(true);

        given(channelPolicies.getPolicyFor(attempt)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt.getAlias())).willReturn(attempts);

        final VerificationAttempts updateAttempts = mock(VerificationAttempts.class);
        given(attempts.add(attempt)).willReturn(updateAttempts);

        final LockoutState initialState = buildNotLockedState();
        final CalculateLockoutStateRequest initialRequest = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(attempts)).willReturn(initialRequest);
        given(lockoutPolicy.calculateLockoutState(initialRequest)).willReturn(initialState);

        final LockoutState updateState = buildNotLockedState();
        final CalculateLockoutStateRequest updateRequest = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(updateAttempts)).willReturn(updateRequest);
        given(lockoutPolicy.calculateLockoutState(updateRequest)).willReturn(updateState);

        final LockoutState lockoutState = lockoutStateService.register(attempt);

        assertThat(lockoutState).isEqualTo(updateState);
        verify(dao).save(updateAttempts);
    }

    @Test
    public void shouldRegisterMultipleAttempts() {
        final VerificationAttempt attempt1 = buildAttempt();
        final VerificationAttempt attempt2 = buildAttempt();
        final Collection<VerificationAttempt> inputAttempts = Arrays.asList(attempt1, attempt2);

        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);
        given(lockoutPolicy.appliesToAllAliases()).willReturn(true);
        given(channelPolicies.getPolicyFor(attempt1)).willReturn(lockoutPolicy);
        given(channelPolicies.getPolicyFor(attempt2)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts1 = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt1.getAlias())).willReturn(attempts1);
        final VerificationAttempts attempts2 = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt2.getAlias())).willReturn(attempts2);

        final LockoutState state1 = buildLockedState();
        final CalculateLockoutStateRequest request1 = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(attempts1)).willReturn(request1);
        given(lockoutPolicy.calculateLockoutState(request1)).willReturn(state1);

        final LockoutState state2 = buildLockedState();
        final CalculateLockoutStateRequest request2 = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(attempts2)).willReturn(request2);
        given(lockoutPolicy.calculateLockoutState(request2)).willReturn(state2);

        final LockoutState lockoutState = lockoutStateService.register(inputAttempts);

        assertThat(lockoutState).isEqualTo(state2);
    }

    @Test
    public void shouldResetAttempts() {
        final LockoutStateRequest request = buildRequest();

        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);
        given(lockoutPolicy.appliesToAllAliases()).willReturn(true);
        given(channelPolicies.getPolicyFor(request)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(request.getAlias())).willReturn(attempts);

        final VerificationAttempts resetAttempts = mock(VerificationAttempts.class);
        given(lockoutPolicy.reset(attempts)).willReturn(resetAttempts);

        final LockoutState expectedLockoutState = mock(LockoutState.class);
        final CalculateLockoutStateRequest calculateStateRequest = mock(CalculateLockoutStateRequest.class);
        given(converter.toRequest(resetAttempts)).willReturn(calculateStateRequest);
        given(lockoutPolicy.calculateLockoutState(calculateStateRequest)).willReturn(expectedLockoutState);

        final LockoutState lockoutState = lockoutStateService.reset(request);

        assertThat(lockoutState).isEqualTo(expectedLockoutState);
        verify(dao).save(resetAttempts);
    }

    private VerificationAttempt buildAttempt() {
        final Alias alias = mock(Alias.class);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getChannelId()).willReturn(CHANNEL_ID);
        given(attempt.getAlias()).willReturn(alias);
        return attempt;
    }

    private VerificationAttempt buildSuccessfulAttempt() {
        final VerificationAttempt attempt = buildAttempt();
        given(attempt.isSuccessful()).willReturn(true);
        return attempt;
    }

    private LockoutStateRequest buildRequest() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        final Alias alias = mock(Alias.class);
        given(request.getChannelId()).willReturn(CHANNEL_ID);
        given(request.getAlias()).willReturn(alias);
        return request;
    }

    private LockoutState buildNotLockedState() {
        return mock(DefaultLockoutState.class);
    }

    private LockoutState buildLockedState() {
        final LockoutState state = mock(DefaultLockoutState.class);
        given(state.isLocked()).willReturn(true);
        return state;
    }

}
