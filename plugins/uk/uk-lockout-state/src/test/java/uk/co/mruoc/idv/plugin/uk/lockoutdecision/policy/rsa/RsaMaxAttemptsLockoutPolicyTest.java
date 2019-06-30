package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutType;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class RsaMaxAttemptsLockoutPolicyTest {

    private final LockoutPolicy policy = new RsaMaxAttemptsLockoutPolicy(AliasType.Names.CREDIT_CARD_NUMBER);

    @Test
    public void shouldHaveMaxAttemptsType() {
        assertThat(policy.getType()).isEqualTo(LockoutType.MAX_ATTEMPTS);
    }

    @Test
    public void shouldApplyToAnyActivityAndMethodForAliasesMatchingAliasType() {
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAliasTypeName()).willReturn(AliasType.Names.CREDIT_CARD_NUMBER);

        final boolean appliesToAttempt = policy.appliesTo(request);

        assertThat(appliesToAttempt).isTrue();
    }

    @Test
    public void shouldNotApplyIfAliasTypeDoesNotMatch() {
        final Alias alias = new IdvIdAlias();
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAlias()).willReturn(alias);

        final boolean appliesToAttempt = policy.appliesTo(request);

        assertThat(appliesToAttempt).isFalse();
    }

    @Test
    public void shouldPopulateLockoutType() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Collections.emptyList())
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getType()).isEqualTo(LockoutType.MAX_ATTEMPTS);
    }

    @Test
    public void shouldPopulateIdvId() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(UUID.randomUUID())
                .attempts(Collections.emptyList())
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getIdvId()).isEqualTo(attempts.getIdvId());
    }

    @Test
    public void shouldPopulateLockoutStateId() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .lockoutStateId(UUID.randomUUID())
                .attempts(Collections.emptyList())
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getId()).isEqualTo(attempts.getLockoutStateId());
    }

    @Test
    public void shouldReturnNumberOfFailedAttempts() {
        final Alias alias = new TokenizedCreditCardNumberAlias("1234567890123456");
        final VerificationAttempt attempt1 = buildAttempt(alias);
        final VerificationAttempt attempt2 = buildAttempt(alias);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .alias(alias)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

    @Test
    public void shouldBeLockedIfThreeOrMoreFailedAttempts() {
        final Alias alias = new TokenizedCreditCardNumberAlias("1234567890123456");
        final VerificationAttempt attempt1 = buildAttempt(alias);
        final VerificationAttempt attempt2 = buildAttempt(alias);
        final VerificationAttempt attempt3 = buildAttempt(alias);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2, attempt3))
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .alias(alias)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.isLocked());
    }

    @Test
    public void shouldRemoveApplicableAttemptsOnReset() {
        final Alias alias = new TokenizedCreditCardNumberAlias("1234567890123456");
        final VerificationAttempt attempt1 = buildAttempt(alias);
        final VerificationAttempt attempt2 = buildAttempt(new IdvIdAlias());
        final VerificationAttempt attempt3 = buildAttempt(alias);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2, attempt3))
                .build();

        final VerificationAttempts resetAttempts = policy.reset(alias, attempts);

        assertThat(resetAttempts).containsExactly(attempt2);
    }

    private VerificationAttempt buildAttempt(final Alias alias) {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn("ANY");
        given(attempt.getAlias()).willReturn(alias);
        given(attempt.getAliasTypeName()).willReturn(alias.getTypeName());
        return attempt;
    }

}
