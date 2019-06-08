package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

@ToString
@Slf4j
public class DefaultLockoutPolicy implements LockoutPolicy {

    private static final String ALL = "ALL";

    private final LockoutStateCalculator stateCalculator;
    private final Collection<String> aliasTypes;
    private final Collection<String> activities;

    private final boolean appliesToAllAliases;
    private final boolean appliesToAllActivities;

    @Builder
    public DefaultLockoutPolicy(final LockoutStateCalculator stateCalculator,
                                final Collection<String> aliasTypes,
                                final Collection<String> activities) {
        this.stateCalculator = stateCalculator;
        this.aliasTypes = aliasTypes;
        this.activities = activities;

        this.appliesToAllAliases = aliasTypes.contains(ALL);
        this.appliesToAllActivities = activities.contains(ALL);
    }

    @Override
    public LockoutState calculateLockoutState(final CalculateLockoutStateRequest request) {
        final VerificationAttempts applicableAttempts = getApplicableAttempts(request.getAttempts());
        final CalculateLockoutStateRequest updatedRequest = CalculateLockoutStateRequest.builder()
                .attempts(applicableAttempts)
                .timestamp(request.getTimestamp())
                .build();
        return stateCalculator.calculateLockoutState(updatedRequest);
    }

    @Override
    public String getType() {
        return stateCalculator.getType();
    }

    @Override
    public boolean appliesTo(final LockoutStateRequest request) {
        log.info("checking that request {} applies to policy {}", request, this);
        return appliesToAlias(request.getAliasTypeName()) &&
                appliesToActivity(request.getActivityType());
    }

    @Override
    public VerificationAttempts reset(final VerificationAttempts attempts) {
        final Collection<VerificationAttempt> attemptsToRemove = getApplicableAttemptsCollection(attempts);
        log.info("removing attempts {}", attemptsToRemove);
        return attempts.removeAll(attemptsToRemove);
    }

    private VerificationAttempts getApplicableAttempts(final VerificationAttempts attempts) {
        final Collection<VerificationAttempt> applicableAttempts = getApplicableAttemptsCollection(attempts);
        log.info("returning applicable attempts {}", applicableAttempts);
        return VerificationAttempts.builder()
                .idvId(attempts.getIdvId())
                .lockoutStateId(attempts.getLockoutStateId())
                .attempts(applicableAttempts)
                .build();
    }

    private Collection<VerificationAttempt> getApplicableAttemptsCollection(final VerificationAttempts attempts) {
        return attempts.stream()
                .filter(this::appliesTo)
                .collect(Collectors.toList());
    }

    private boolean appliesToAlias(final String aliasType) {
        return appliesToAllAliases || aliasTypes.contains(aliasType);
    }

    private boolean appliesToActivity(final String activity) {
        return appliesToAllActivities || activities.contains(activity);
    }

}
