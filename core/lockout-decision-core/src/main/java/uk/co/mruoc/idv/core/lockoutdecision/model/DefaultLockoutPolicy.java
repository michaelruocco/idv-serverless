package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.util.Collection;
import java.util.stream.Collectors;

@ToString
@Slf4j
public class DefaultLockoutPolicy implements LockoutPolicy {

    private static final String ALL = "ALL";

    private final LockoutStateCalculator stateCalculator;
    private final String aliasTypeName;
    private final Collection<String> activities;

    private final boolean appliesToAllAliases;
    private final boolean appliesToAllActivities;

    @Builder
    public DefaultLockoutPolicy(final LockoutStateCalculator stateCalculator,
                                final String aliasTypeName,
                                final Collection<String> activities) {
        this.stateCalculator = stateCalculator;
        this.aliasTypeName = aliasTypeName;
        this.activities = activities;

        this.appliesToAllAliases = aliasTypeName.equals(ALL);
        this.appliesToAllActivities = activities.contains(ALL);
    }

    @Override
    public LockoutState calculateLockoutState(final CalculateLockoutStateRequest request) {
        final VerificationAttempts applicableAttempts = getApplicableAttempts(request);
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
    public String getAliasTypeName() {
        return aliasTypeName;
    }

    @Override
    public boolean appliesTo(final LockoutStateRequest request) {
        log.info("checking request {} applies to policy {}", request, this);
        return appliesToAlias(request.getAliasTypeName()) &&
                appliesToActivity(request.getActivityType());
    }

    @Override
    public VerificationAttempts reset(final Alias alias, final VerificationAttempts attempts) {
        final Collection<VerificationAttempt> attemptsToRemove = getApplicableAttemptsCollection(alias, attempts);
        log.info("removing attempts {}", attemptsToRemove);
        return attempts.removeAll(attemptsToRemove);
    }

    private VerificationAttempts getApplicableAttempts(final CalculateLockoutStateRequest request) {
        return getApplicableAttempts(request.getAlias(), request.getAttempts());
    }

    private VerificationAttempts getApplicableAttempts(final Alias alias, final VerificationAttempts attempts) {
        final Collection<VerificationAttempt> applicableAttempts = getApplicableAttemptsCollection(alias, attempts);
        log.info("returning applicable attempts {}", applicableAttempts);
        return VerificationAttempts.builder()
                .idvId(attempts.getIdvId())
                .lockoutStateId(attempts.getLockoutStateId())
                .attempts(applicableAttempts)
                .build();
    }

    private Collection<VerificationAttempt> getApplicableAttemptsCollection(final Alias alias, final VerificationAttempts attempts) {
        return attempts.stream()
                .filter(attempt -> this.appliesTo(alias, attempt))
                .collect(Collectors.toList());
    }

    private boolean appliesTo(final Alias alias, final LockoutStateRequest request) {
        if (appliesToAllAliases || appliesTo(request)) {
            log.info("returning request {} applies to policy {} true", request, this);
            return true;
        }
        log.info("checking request alias {} matches alias {}", request.getAlias(), alias);
        return alias.equals(request.getAlias());
    }

    private boolean appliesToAlias(final String aliasTypeName) {
        return appliesToAllAliases || this.aliasTypeName.equals(aliasTypeName);
    }

    private boolean appliesToActivity(final String activity) {
        return appliesToAllActivities || activities.contains(activity);
    }

}
