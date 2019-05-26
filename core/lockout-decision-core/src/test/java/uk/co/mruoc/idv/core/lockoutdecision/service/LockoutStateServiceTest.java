package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LockoutStateServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final VerificationAttemptsConverter converter = mock(VerificationAttemptsConverter.class);
    private final LockoutPoliciesService policiesService = mock(LockoutPoliciesService.class);
    private final LoadVerificationAttemptsService loadAttemptsService = mock(LoadVerificationAttemptsService.class);
    private final VerificationAttemptsDao dao = mock(VerificationAttemptsDao.class);

    private final LockoutStateService attemptService = LockoutStateService.builder()
            .converter(converter)
            .policiesService(policiesService)
            .loadAttemptsService(loadAttemptsService)
            .dao(dao)
            .build();

    @Test
    public void shouldLoadLockoutStateForAttempt() {
        final VerificationAttempt attempt = buildAttempt();
        final LockoutState expectedLockoutState = buildLockedState();
        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);
        given(channelPolicies.getPolicyFor(attempt)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt.getAlias())).willReturn(attempts);

        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(converter.toRequest(attempts)).willReturn(request);
        given(lockoutPolicy.calculateLockoutState(request)).willReturn(expectedLockoutState);

        final LockoutState lockoutState = attemptService.load(attempt);

        assertThat(lockoutState).isEqualTo(expectedLockoutState);
    }

    @Test
    public void shouldReturnInitialLockoutStateIfInitialStateIsAlreadyLocked() {
        final VerificationAttempt attempt = buildAttempt();
        final LockoutState initialState = buildLockedState();
        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);
        given(channelPolicies.getPolicyFor(attempt)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt.getAlias())).willReturn(attempts);

        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(converter.toRequest(attempts)).willReturn(request);
        given(lockoutPolicy.calculateLockoutState(request)).willReturn(initialState);

        final LockoutState lockoutState = attemptService.register(attempt);

        assertThat(lockoutState).isEqualTo(initialState);
    }

    @Test
    public void shouldResetLockoutStateIfAttemptIsSuccessfulAndInitialStateIsNotLocked() {
        final VerificationAttempt attempt = buildSuccessfulAttempt();
        final LockoutState initialState = buildNotLockedState();
        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);

        given(channelPolicies.getPolicyFor(attempt)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt.getAlias())).willReturn(attempts);

        final VerificationAttempts resetAttempts = mock(VerificationAttempts.class);
        given(lockoutPolicy.reset(attempts)).willReturn(resetAttempts);

        final LockoutStateRequest initialRequest = mock(LockoutStateRequest.class);
        given(converter.toRequest(attempts)).willReturn(initialRequest);
        given(lockoutPolicy.calculateLockoutState(initialRequest)).willReturn(initialState);

        final LockoutState resetState = buildNotLockedState();
        final LockoutStateRequest resetRequest = mock(LockoutStateRequest.class);
        given(converter.toRequest(resetAttempts)).willReturn(resetRequest);
        given(lockoutPolicy.calculateLockoutState(resetRequest)).willReturn(resetState);

        final LockoutState lockoutState = attemptService.register(attempt);

        assertThat(lockoutState).isEqualTo(resetState);
        verify(dao).save(resetAttempts);
    }

    @Test
    public void shouldUpdateLockoutStateIfAttemptIsUnsuccessfulAndInitialStateIsNotLocked() {
        final VerificationAttempt attempt = buildAttempt();
        final LockoutState initialState = buildNotLockedState();
        final ChannelLockoutPolicies channelPolicies = mock(ChannelLockoutPolicies.class);
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);

        given(channelPolicies.getPolicyFor(attempt)).willReturn(lockoutPolicy);
        given(policiesService.getPoliciesForChannel(CHANNEL_ID)).willReturn(channelPolicies);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(loadAttemptsService.load(attempt.getAlias())).willReturn(attempts);

        final VerificationAttempts updateAttempts = mock(VerificationAttempts.class);
        given(attempts.add(attempt)).willReturn(updateAttempts);

        final LockoutStateRequest initialRequest = mock(LockoutStateRequest.class);
        given(converter.toRequest(attempts)).willReturn(initialRequest);
        given(lockoutPolicy.calculateLockoutState(initialRequest)).willReturn(initialState);

        final LockoutState updateState = buildNotLockedState();
        final LockoutStateRequest updateRequest = mock(LockoutStateRequest.class);
        given(converter.toRequest(updateAttempts)).willReturn(updateRequest);
        given(lockoutPolicy.calculateLockoutState(updateRequest)).willReturn(updateState);

        final LockoutState lockoutState = attemptService.register(attempt);

        assertThat(lockoutState).isEqualTo(updateState);
        verify(dao).save(updateAttempts);
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

    private LockoutState buildNotLockedState() {
        return mock(LockoutState.class);
    }

    private LockoutState buildLockedState() {
        final LockoutState state = mock(LockoutState.class);
        given(state.isLocked()).willReturn(true);
        return state;
    }

}
