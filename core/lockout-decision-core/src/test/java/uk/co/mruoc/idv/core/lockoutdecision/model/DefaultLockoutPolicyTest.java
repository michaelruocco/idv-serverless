package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultLockoutPolicyTest {

    private static final String ACTIVITY = "ACTIVITY";
    private static final String VERIFICATION_METHOD = "VERIFICATION_METHOD";
    private static final String ALL = "ALL";

    @Test
    public void shouldReturnFalseIfAttemptAliasDoesNotMatchPolicy() {
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypes(Collections.singleton(AliasType.Names.CREDIT_CARD_NUMBER))
                .methods(Collections.emptyList())
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(ACTIVITY);
        given(attempt.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnFalseIfAttemptActivityDoesNotMatchPolicy() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypes(Collections.singleton(aliasType))
                .methods(Collections.emptyList())
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn("OTHER_ACTIVITY");
        given(attempt.getAliasTypeName()).willReturn(aliasType);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnTrueIfAttemptAliasAndActivityMatchesPolicyAndMethodIsNotProvided() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypes(Collections.singleton(aliasType))
                .methods(Collections.emptyList())
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(ACTIVITY);
        given(attempt.getAliasTypeName()).willReturn(aliasType);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isTrue();
    }

    @Test
    public void shouldReturnFalseIfAttemptAliasAndActivityMatchesPolicyAndMethodDoesNotMatch() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypes(Collections.singleton(aliasType))
                .methods(Collections.singleton(VERIFICATION_METHOD))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(ACTIVITY);
        given(attempt.getAliasTypeName()).willReturn(aliasType);
        given(attempt.getMethodName()).willReturn(Optional.of("OTHER_VERIFICATION_METHOD"));

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnTrueIfAttemptAliasAndActivityAndMethodAllMatchPolicy() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypes(Collections.singleton(aliasType))
                .methods(Collections.singleton(VERIFICATION_METHOD))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(ACTIVITY);
        given(attempt.getAliasTypeName()).willReturn(aliasType);
        given(attempt.getMethodName()).willReturn(Optional.of(VERIFICATION_METHOD));

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isTrue();
    }

    @Test
    public void shouldReturnTrueIfPolicyMatchesAllActivitiesAliasesAndMethods() {
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ALL))
                .aliasTypes(Collections.singleton(ALL))
                .methods(Collections.singleton(ALL))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(ACTIVITY);
        given(attempt.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);
        given(attempt.getMethodName()).willReturn(Optional.of(VERIFICATION_METHOD));

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
                .methods(Collections.emptyList())
                .stateCalculator(calculator)
                .build();

        assertThat(policy.getType()).isEqualTo(type);
    }

    @Test
    public void shouldRemoveApplicableAttemptsOnReset() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        given(attempt1.getMethodName()).willReturn(Optional.empty());
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        given(attempt2.getMethodName()).willReturn(Optional.empty());

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ALL))
                .aliasTypes(Collections.singleton(ALL))
                .methods(Collections.singleton(ALL))
                .build();

        final VerificationAttempts resetAttempts = policy.reset(attempts);

        assertThat(resetAttempts).isEmpty();
    }

    @Test
    public void shouldPassApplicableAttemptsToLockoutStateCalculator() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        given(attempt1.getMethodName()).willReturn(Optional.empty());
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        given(attempt2.getMethodName()).willReturn(Optional.empty());

        final Collection<VerificationAttempt> attemptCollection = Arrays.asList(attempt1, attempt2);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(attemptCollection)
                .build();
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutStateCalculator calculator = mock(LockoutStateCalculator.class);

        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ALL))
                .aliasTypes(Collections.singleton(ALL))
                .methods(Collections.singleton(ALL))
                .stateCalculator(calculator)
                .build();

        final ArgumentCaptor<LockoutStateRequest> captor = ArgumentCaptor.forClass(LockoutStateRequest.class);
        final LockoutState expectedState = mock(LockoutState.class);
        given(calculator.calculateLockoutState(captor.capture())).willReturn(expectedState);

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
        assertThat(captor.getValue().getAttempts()).containsExactlyElementsOf(attemptCollection);
    }

}
