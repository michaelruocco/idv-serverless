package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutType;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class RsaMaxAttemptsLockoutPolicyTest {

    private final LockoutPolicy policy = new RsaMaxAttemptsLockoutPolicy();

    @Test
    public void shouldHaveMaxAttemptsType() {
        assertThat(policy.getType()).isEqualTo(LockoutType.MAX_ATTEMPTS);
    }

    @Test
    public void shouldApplyToAnyActivityAndMethodForDebitCardNumberAliases() {
        final VerificationAttempt attempt = buildAttempt(AliasType.Names.DEBIT_CARD_NUMBER);

        final boolean appliesToAttempt = policy.appliesTo(attempt);

        assertThat(appliesToAttempt).isTrue();
    }

    @Test
    public void shouldApplyToAnyActivityAndMethodForCreditCardNumberAliases() {
        final VerificationAttempt attempt = buildAttempt(AliasType.Names.CREDIT_CARD_NUMBER);

        final boolean appliesToAttempt = policy.appliesTo(attempt);

        assertThat(appliesToAttempt).isTrue();
    }

    @Test
    public void shouldNotApplyForNonCardNumberAliases() {
        final VerificationAttempt attempt = buildAttempt(AliasType.Names.IDV_ID);

        final boolean appliesToAttempt = policy.appliesTo(attempt);

        assertThat(appliesToAttempt).isFalse();
    }

    @Test
    public void shouldPopulateLockoutType() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Collections.emptyList())
                .build();
        final LockoutStateRequest request = LockoutStateRequest.builder()
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
        final LockoutStateRequest request = LockoutStateRequest.builder()
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
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getId()).isEqualTo(attempts.getLockoutStateId());
    }

    @Test
    public void shouldReturnNumberOfFailedAttempts() {
        final VerificationAttempt attempt1 = buildAttempt(AliasType.Names.CREDIT_CARD_NUMBER);
        final VerificationAttempt attempt2 = buildAttempt(AliasType.Names.DEBIT_CARD_NUMBER);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

    @Test
    public void shouldBeLockedIfThreeOrMoreFailedAttempts() {
        final VerificationAttempt attempt1 = buildAttempt(AliasType.Names.CREDIT_CARD_NUMBER);
        final VerificationAttempt attempt2 = buildAttempt(AliasType.Names.DEBIT_CARD_NUMBER);
        final VerificationAttempt attempt3 = buildAttempt(AliasType.Names.CREDIT_CARD_NUMBER);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2, attempt3))
                .build();
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.isLocked());
    }

    @Test
    public void shouldRemoveApplicableAttemptsOnReset() {
        final VerificationAttempt attempt1 = buildAttempt(AliasType.Names.CREDIT_CARD_NUMBER);
        final VerificationAttempt attempt2 = buildAttempt(AliasType.Names.IDV_ID);
        final VerificationAttempt attempt3 = buildAttempt(AliasType.Names.CREDIT_CARD_NUMBER);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2, attempt3))
                .build();

        final VerificationAttempts resetAttempts = policy.reset(attempts);

        assertThat(resetAttempts).containsExactly(attempt2);
    }

    private VerificationAttempt buildAttempt(final String aliasTypeName) {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getMethodName()).willReturn(Optional.of("ANY"));
        given(attempt.getActivityType()).willReturn("ANY");
        given(attempt.getAliasTypeName()).willReturn(aliasTypeName);
        return attempt;
    }

}
