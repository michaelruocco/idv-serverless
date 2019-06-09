package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutType;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class RsaMaxAttemptsLockoutPolicyTest {

    private static final String ALIAS_TYPE = AliasType.Names.DEBIT_CARD_NUMBER;

    private final LockoutPolicy policy = new RsaMaxAttemptsLockoutPolicy(ALIAS_TYPE);

    @Test
    public void shouldHaveMaxAttemptsType() {
        assertThat(policy.getType()).isEqualTo(LockoutType.MAX_ATTEMPTS);
    }

    @Test
    public void shouldApplyToAnyActivityAndMethodForAliasesMatchingAliasType() {
        final VerificationAttempt attempt = buildAttempt(ALIAS_TYPE);

        final boolean appliesToAttempt = policy.appliesTo(attempt);

        assertThat(appliesToAttempt).isTrue();
    }

    @Test
    public void shouldNotApplyIfAliasTypeDoesNotMatch() {
        final VerificationAttempt attempt = buildAttempt(AliasType.Names.IDV_ID);

        final boolean appliesToAttempt = policy.appliesTo(attempt);

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
        final VerificationAttempt attempt1 = buildAttempt(ALIAS_TYPE);
        final VerificationAttempt attempt2 = buildAttempt(ALIAS_TYPE);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

    @Test
    public void shouldBeLockedIfThreeOrMoreFailedAttempts() {
        final VerificationAttempt attempt1 = buildAttempt(ALIAS_TYPE);
        final VerificationAttempt attempt2 = buildAttempt(ALIAS_TYPE);
        final VerificationAttempt attempt3 = buildAttempt(ALIAS_TYPE);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2, attempt3))
                .build();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.isLocked());
    }

    @Test
    public void shouldRemoveApplicableAttemptsOnReset() {
        final VerificationAttempt attempt1 = buildAttempt(ALIAS_TYPE);
        final VerificationAttempt attempt2 = buildAttempt(AliasType.Names.IDV_ID);
        final VerificationAttempt attempt3 = buildAttempt(ALIAS_TYPE);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2, attempt3))
                .build();

        final VerificationAttempts resetAttempts = policy.reset(attempts);

        assertThat(resetAttempts).containsExactly(attempt2);
    }

    private VerificationAttempt buildAttempt(final String aliasTypeName) {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getActivityType()).willReturn("ANY");
        given(attempt.getAliasTypeName()).willReturn(aliasTypeName);
        return attempt;
    }

}
