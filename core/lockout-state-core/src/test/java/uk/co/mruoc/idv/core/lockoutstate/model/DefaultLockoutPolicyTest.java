package uk.co.mruoc.idv.core.lockoutstate.model;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutstate.model.DefaultLockoutPolicy.DefaultLockoutPolicyBuilder;
import uk.co.mruoc.idv.core.lockoutstate.service.VerificationAttemptConverter;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultLockoutPolicyTest {

    private static final String ACTIVITY = "ACTIVITY";
    private static final String ALL = "ALL";

    private final VerificationAttemptConverter attemptConverter = mock(VerificationAttemptConverter.class);

    private final DefaultLockoutPolicyBuilder policyBuilder = DefaultLockoutPolicy.builder()
            .attemptConverter(attemptConverter);
    @Test
    public void shouldReturnFalseIfRequestAliasDoesNotMatchPolicy() {
        final LockoutPolicy policy = policyBuilder
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypeName(AliasType.Names.CREDIT_CARD_NUMBER)
                .build();

        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getActivityType()).willReturn(ACTIVITY);
        given(request.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);

        final boolean appliesTo = policy.appliesTo(request);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnFalseIfAttemptActivityDoesNotMatchPolicy() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = policyBuilder
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypeName(aliasType)
                .build();

        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getActivityType()).willReturn("OTHER_ACTIVITY");
        given(request.getAliasTypeName()).willReturn(aliasType);

        final boolean appliesTo = policy.appliesTo(request);

        assertThat(appliesTo).isFalse();
    }

    @Test
    public void shouldReturnTrueIfAttemptAliasAndActivityMatchesPolicy() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;
        final LockoutPolicy policy = policyBuilder
                .activities(Collections.singleton(ACTIVITY))
                .aliasTypeName(aliasType)
                .build();

        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getActivityType()).willReturn(ACTIVITY);
        given(request.getAliasTypeName()).willReturn(aliasType);

        final boolean appliesTo = policy.appliesTo(request);

        assertThat(appliesTo).isTrue();
    }

    @Test
    public void shouldReturnTrueIfPolicyMatchesAllActivitiesAliases() {
        final LockoutPolicy policy = policyBuilder
                .activities(Collections.singleton(ALL))
                .aliasTypeName(ALL)
                .build();

        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getActivityType()).willReturn(ACTIVITY);
        given(request.getAliasTypeName()).willReturn(AliasType.Names.DEBIT_CARD_NUMBER);

        final boolean appliesTo = policy.appliesTo(request);

        assertThat(appliesTo).isTrue();
    }

    @Test
    public void shouldReturnTypeFromCalculator() {
        final LockoutStateCalculator calculator = mock(LockoutStateCalculator.class);
        final String type = LockoutType.MAX_ATTEMPTS;
        given(calculator.getType()).willReturn(type);

        final LockoutPolicy policy = policyBuilder
                .activities(Collections.emptyList())
                .aliasTypeName("")
                .stateCalculator(calculator)
                .build();

        assertThat(policy.getType()).isEqualTo(type);
    }

    @Test
    public void shouldRemoveApplicableAttemptsOnReset() {
        final Alias alias = mock(Alias.class);
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final LockoutPolicy policy = policyBuilder
                .activities(Collections.singleton(ALL))
                .aliasTypeName(ALL)
                .build();


        final VerificationAttempts resetAttempts = policy.reset(alias, attempts);

        assertThat(resetAttempts).isEmpty();
    }

    @Test
    public void shouldRemoveApplicableAttemptsForSpecificAliasOnReset() {
        final String aliasTypeName = "aliasTypeName";
        final Alias alias = mock(Alias.class);
        given(alias.getTypeName()).willReturn(aliasTypeName);
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final LockoutPolicy policy = policyBuilder
                .activities(Collections.singleton(ALL))
                .aliasTypeName(aliasTypeName)
                .build();

        final LockoutStateRequest request1 = mock(LockoutStateRequest.class);
        given(attemptConverter.toLockoutStateRequest(attempt1)).willReturn(request1);
        given(request1.getAlias()).willReturn(alias);
        final LockoutStateRequest request2 = mock(LockoutStateRequest.class);
        given(attemptConverter.toLockoutStateRequest(attempt2)).willReturn(request2);
        given(request2.getAlias()).willReturn(mock(Alias.class));

        final VerificationAttempts resetAttempts = policy.reset(alias, attempts);

        assertThat(resetAttempts).containsExactly(attempt2);
    }

    @Test
    public void shouldPassApplicableAttemptsToLockoutStateCalculator() {
        final Alias alias = mock(Alias.class);
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);

        final Collection<VerificationAttempt> attemptCollection = Arrays.asList(attempt1, attempt2);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(attemptCollection)
                .build();

        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .alias(alias)
                .build();

        final LockoutStateCalculator calculator = mock(LockoutStateCalculator.class);

        final LockoutPolicy policy = policyBuilder
                .activities(Collections.singleton(ALL))
                .aliasTypeName(ALL)
                .stateCalculator(calculator)
                .build();

        final ArgumentCaptor<CalculateLockoutStateRequest> captor = ArgumentCaptor.forClass(CalculateLockoutStateRequest.class);
        final LockoutState expectedState = mock(DefaultLockoutState.class);
        given(calculator.calculateLockoutState(captor.capture())).willReturn(expectedState);

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
        assertThat(captor.getValue().getAttempts()).containsExactlyElementsOf(attemptCollection);
    }

    @Test
    public void shouldReturnAliasType() {
        final String aliasType = AliasType.Names.CREDIT_CARD_NUMBER;

        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .aliasTypeName(aliasType)
                .activities(Collections.emptyList())
                .build();

        assertThat(policy.getAliasTypeName()).isEqualTo(aliasType);
    }

}
