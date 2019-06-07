package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultLockoutPolicyTest {

    private static final String ACTIVITY = "ACTIVITY";
    private static final String ALL = "ALL";

    @Test
    public void shouldReturnFalseIfAttemptAliasDoesNotMatchPolicy() {
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypes(Collections.singleton(AliasType.Names.CREDIT_CARD_NUMBER))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(ACTIVITY);
        given(attempt.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnFalseIfAttemptActivityDoesNotMatchPolicy() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypes(Collections.singleton(aliasType))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn("OTHER_ACTIVITY");
        given(attempt.getAliasTypeName()).willReturn(aliasType);

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnTrueIfAttemptAliasAndActivityMatchesPolicy() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypes(Collections.singleton(aliasType))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(ACTIVITY);
        given(attempt.getAliasTypeName()).willReturn(aliasType);

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isTrue();
    }

    @Test
    public void shouldReturnTrueIfPolicyMatchesAllActivitiesAliases() {
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ALL))
                .aliasTypes(Collections.singleton(ALL))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(ACTIVITY);
        given(attempt.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isTrue();
    }

    @Test
    public void shouldReturnTypeFromCalculator() {
        final LockoutStateCalculator calculator = mock(LockoutStateCalculator.class);
        final String type = LockoutType.MAX_ATTEMPTS;
        given(calculator.getType()).willReturn(type);

        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.emptyList())
                .aliasTypes(Collections.emptyList())
                .stateCalculator(calculator)
                .build();

        assertThat(policy.getType()).isEqualTo(type);
    }

    @Test
    public void shouldRemoveApplicableAttemptsOnReset() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ALL))
                .aliasTypes(Collections.singleton(ALL))
                .build();

        final VerificationAttempts resetAttempts = policy.reset(attempts);

        assertThat(resetAttempts).isEmpty();
    }

    @Test
    public void shouldPassApplicableAttemptsToLockoutStateCalculator() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);

        final Collection<VerificationAttempt> attemptCollection = Arrays.asList(attempt1, attempt2);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(attemptCollection)
                .build();

        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutStateCalculator calculator = mock(LockoutStateCalculator.class);

        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ALL))
                .aliasTypes(Collections.singleton(ALL))
                .stateCalculator(calculator)
                .build();

        final ArgumentCaptor<CalculateLockoutStateRequest> captor = ArgumentCaptor.forClass(CalculateLockoutStateRequest.class);
        final LockoutState expectedState = mock(DefaultLockoutState.class);
        given(calculator.calculateLockoutState(captor.capture())).willReturn(expectedState);

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
        assertThat(captor.getValue().getAttempts()).containsExactlyElementsOf(attemptCollection);
    }

}
