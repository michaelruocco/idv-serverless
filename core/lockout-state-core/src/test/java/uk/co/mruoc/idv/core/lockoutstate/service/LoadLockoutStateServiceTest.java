package uk.co.mruoc.idv.core.lockoutstate.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutstate.model.DefaultLockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LoadLockoutStateServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final LockoutStateCalculationService calculationService = mock(LockoutStateCalculationService.class);
    private final LockoutPoliciesService policiesService = mock(LockoutPoliciesService.class);
    private final VerificationAttemptsService attemptsService = mock(VerificationAttemptsService.class);

    private final LoadLockoutStateService lockoutStateService = LoadLockoutStateService.builder()
            .calculationService(calculationService)
            .policiesService(policiesService)
            .attemptsService(attemptsService)
            .build();


    @Test
    public void shouldLoadLockoutStateForAttempt() {
        final LockoutStateRequest request = buildRequest();
        final LockoutPolicy lockoutPolicy = mock(LockoutPolicy.class);
        given(policiesService.getPolicy(request)).willReturn(lockoutPolicy);

        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(attemptsService.load(request.getAlias())).willReturn(attempts);

        final LockoutState expectedLockoutState = buildLockedState();
        given(calculationService.calculateLockoutState(request.getAlias(), lockoutPolicy, attempts)).willReturn(expectedLockoutState);

        final LockoutState lockoutState = lockoutStateService.load(request);

        assertThat(lockoutState).isEqualTo(expectedLockoutState);
    }

    private LockoutStateRequest buildRequest() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        final Alias alias = mock(Alias.class);
        given(request.getChannelId()).willReturn(CHANNEL_ID);
        given(request.getAlias()).willReturn(alias);
        return request;
    }

    private LockoutState buildLockedState() {
        final LockoutState state = mock(DefaultLockoutState.class);
        given(state.isLocked()).willReturn(true);
        return state;
    }

}
