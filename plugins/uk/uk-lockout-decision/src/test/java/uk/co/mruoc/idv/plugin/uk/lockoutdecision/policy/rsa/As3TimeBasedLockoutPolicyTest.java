package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockedTimeBasedLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutType;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class As3TimeBasedLockoutPolicyTest {

    private final LockoutPolicy policy = new As3TimeBasedLockoutPolicy();

    @Test
    public void shouldHaveTimeBasedIntervalType() {
        assertThat(policy.getType()).isEqualTo(LockoutType.TIME_BASED_INTERVAL);
    }

    @Test
    public void shouldApplyToAnyActivityAndMethodAndAlias() {
        final VerificationAttempt attempt = buildAttempt(AliasType.Names.DEBIT_CARD_NUMBER);

        final boolean appliesToAttempt = policy.appliesTo(attempt);

        assertThat(appliesToAttempt).isTrue();
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

        assertThat(state.getType()).isEqualTo(LockoutType.TIME_BASED_INTERVAL);
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
    public void shouldReturnNotLockedForOneFailedAttempt() {
        final int numberOfAttempts = 1;
        final VerificationAttempts attempts = buildAttempts(numberOfAttempts);
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .timestamp(Instant.now())
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
        assertThat(state.isLocked()).isFalse();
    }

    @Test
    public void shouldReturnFifteenMinuteLockForThreeFailedAttempts() {
        final int numberOfAttempts = 3;
        final VerificationAttempts attempts = buildAttempts(numberOfAttempts);
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .timestamp(Instant.now())
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
        assertThat(state.isLocked()).isTrue();
        final LockedTimeBasedLockoutState timeBasedLockoutState = (LockedTimeBasedLockoutState) state;
        final Duration fifteenMinutes = Duration.ofMinutes(15);
        assertThat(timeBasedLockoutState.getDuration()).isEqualTo(fifteenMinutes);
        assertThat(timeBasedLockoutState.getLockedUntil()).isEqualTo(attempts.getMostRecentTimestamp().plus(fifteenMinutes));
    }

    @Test
    public void shouldReturnOneHourLockForSixFailedAttempts() {
        final int numberOfAttempts = 6;
        final VerificationAttempts attempts = buildAttempts(numberOfAttempts);
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .timestamp(Instant.now())
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
        assertThat(state.isLocked()).isTrue();
        final LockedTimeBasedLockoutState timeBasedLockoutState = (LockedTimeBasedLockoutState) state;
        final Duration fifteenMinutes = Duration.ofHours(1);
        assertThat(timeBasedLockoutState.getDuration()).isEqualTo(fifteenMinutes);
        assertThat(timeBasedLockoutState.getLockedUntil()).isEqualTo(attempts.getMostRecentTimestamp().plus(fifteenMinutes));
    }

    @Test
    public void shouldReturnFourHourLockForNineFailedAttempts() {
        final int numberOfAttempts = 9;
        final VerificationAttempts attempts = buildAttempts(numberOfAttempts);
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .timestamp(Instant.now())
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
        assertThat(state.isLocked()).isTrue();
        final LockedTimeBasedLockoutState timeBasedLockoutState = (LockedTimeBasedLockoutState) state;
        final Duration fifteenMinutes = Duration.ofHours(4);
        assertThat(timeBasedLockoutState.getDuration()).isEqualTo(fifteenMinutes);
        assertThat(timeBasedLockoutState.getLockedUntil()).isEqualTo(attempts.getMostRecentTimestamp().plus(fifteenMinutes));
    }

    @Test
    public void shouldReturnTwentyFourHourLockForTwelveFailedAttempts() {
        final int numberOfAttempts = 12;
        final VerificationAttempts attempts = buildAttempts(numberOfAttempts);
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .timestamp(Instant.now())
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
        assertThat(state.isLocked()).isTrue();
        final LockedTimeBasedLockoutState timeBasedLockoutState = (LockedTimeBasedLockoutState) state;
        final Duration fifteenMinutes = Duration.ofHours(24);
        assertThat(timeBasedLockoutState.getDuration()).isEqualTo(fifteenMinutes);
        assertThat(timeBasedLockoutState.getLockedUntil()).isEqualTo(attempts.getMostRecentTimestamp().plus(fifteenMinutes));
    }

    @Test
    public void shouldReturnTwentyFourHourLockForMoreThanTwelveFailedAttempts() {
        final int numberOfAttempts = 13;
        final VerificationAttempts attempts = buildAttempts(numberOfAttempts);
        final LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .timestamp(Instant.now())
                .build();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state.getNumberOfAttempts()).isEqualTo(attempts.size());
        assertThat(state.isLocked()).isTrue();
        final LockedTimeBasedLockoutState timeBasedLockoutState = (LockedTimeBasedLockoutState) state;
        final Duration fifteenMinutes = Duration.ofHours(24);
        assertThat(timeBasedLockoutState.getDuration()).isEqualTo(fifteenMinutes);
        assertThat(timeBasedLockoutState.getLockedUntil()).isEqualTo(attempts.getMostRecentTimestamp().plus(fifteenMinutes));
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

        assertThat(resetAttempts).isEmpty();
    }

    private VerificationAttempts buildAttempts(final int numberOfAttemptsToBuild) {
        final Collection<VerificationAttempt> attempts = new ArrayList<>();
        for (int i = 0; i < numberOfAttemptsToBuild; i++) {
            attempts.add(buildAttempt(AliasType.Names.IDV_ID));
        }
        return VerificationAttempts.builder()
                .attempts(Collections.unmodifiableCollection(attempts))
                .build();
    }

    private VerificationAttempt buildAttempt(final String aliasTypeName) {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getMethodName()).willReturn(Optional.of("ANY"));
        given(attempt.getActivityType()).willReturn("ANY");
        given(attempt.getAliasTypeName()).willReturn(aliasTypeName);
        given(attempt.getTimestamp()).willReturn(Instant.now());
        return attempt;
    }

}
