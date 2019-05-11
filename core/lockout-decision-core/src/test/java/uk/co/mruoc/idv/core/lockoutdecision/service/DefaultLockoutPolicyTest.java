package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutType;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultLockoutPolicyTest {

    private static final String ALL = "ALL";

    @Test
    public void shouldReturnFalseIfAttemptAliasDoesNotMatchPolicy() {
        final String activity = Activity.Types.LOGIN;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(activity))
                .aliasTypes(Collections.singleton(AliasType.Names.CREDIT_CARD_NUMBER))
                .methods(Collections.emptyList())
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(activity);
        given(attempt.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnFalseIfAttemptActivityDoesNotMatchPolicy() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(Activity.Types.LOGIN))
                .aliasTypes(Collections.singleton(aliasType))
                .methods(Collections.emptyList())
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(Activity.Types.ONLINE_PURCHASE);
        given(attempt.getAliasTypeName()).willReturn(aliasType);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnTrueIfAttemptAliasAndActivityMatchesPolicyAndMethodIsNotProvided() {
        final String activity = Activity.Types.LOGIN;
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(activity))
                .aliasTypes(Collections.singleton(aliasType))
                .methods(Collections.emptyList())
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(activity);
        given(attempt.getAliasTypeName()).willReturn(aliasType);
        given(attempt.getMethodName()).willReturn(Optional.empty());

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isTrue();
    }

    @Test
    public void shouldReturnFalseIfAttemptAliasAndActivityMatchesPolicyAndMethodDoesNotMatch() {
        final String activity = Activity.Types.LOGIN;
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(activity))
                .aliasTypes(Collections.singleton(aliasType))
                .methods(Collections.singleton(VerificationMethod.Names.PHYSICAL_PINSENTRY))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(activity);
        given(attempt.getAliasTypeName()).willReturn(aliasType);
        given(attempt.getMethodName()).willReturn(Optional.of(VerificationMethod.Names.MOBILE_PINSENTRY));

        final boolean appliesTo = policy.appliesTo(attempt);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnTrueIfAttemptAliasAndActivityAndMethodAllMatchPolicy() {
        final String activity = Activity.Types.LOGIN;
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final String method = VerificationMethod.Names.PHYSICAL_PINSENTRY;
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(activity))
                .aliasTypes(Collections.singleton(aliasType))
                .methods(Collections.singleton(method))
                .build();

        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn(activity);
        given(attempt.getAliasTypeName()).willReturn(aliasType);
        given(attempt.getMethodName()).willReturn(Optional.of(method));

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
        given(attempt.getActivityType()).willReturn(Activity.Types.ONLINE_PURCHASE);
        given(attempt.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);
        given(attempt.getMethodName()).willReturn(Optional.of(VerificationMethod.Names.PHYSICAL_PINSENTRY));

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

        final LockoutStateCalculator calculator = mock(LockoutStateCalculator.class);

        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .activities(Collections.singleton(ALL))
                .aliasTypes(Collections.singleton(ALL))
                .methods(Collections.singleton(ALL))
                .stateCalculator(calculator)
                .build();

        final ArgumentCaptor<VerificationAttempts> captor = ArgumentCaptor.forClass(VerificationAttempts.class);
        final LockoutState expectedState = mock(LockoutState.class);
        given(calculator.calculateLockoutState(captor.capture())).willReturn(expectedState);

        final LockoutState state = policy.calculateLockoutState(attempts);

        assertThat(state).isEqualTo(expectedState);
        assertThat(captor.getValue()).containsExactlyElementsOf(attemptCollection);
    }

}
