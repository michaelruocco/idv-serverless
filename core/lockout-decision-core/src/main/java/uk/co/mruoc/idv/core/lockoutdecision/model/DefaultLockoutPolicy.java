package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
@Slf4j
public class DefaultLockoutPolicy implements LockoutPolicy {

    private static final String ALL = "ALL";

    private final LockoutStateCalculator stateCalculator;
    private final Collection<String> aliasTypes;
    private final Collection<String> activities;
    private final Collection<String> methods;

    private final boolean appliesToAllAliases;
    private final boolean appliesToAllActivities;
    private final boolean appliesToAllMethods;

    @Builder
    public DefaultLockoutPolicy(final LockoutStateCalculator stateCalculator,
                                final Collection<String> aliasTypes,
                                final Collection<String> activities,
                                final Collection<String> methods) {
        this.stateCalculator = stateCalculator;
        this.aliasTypes = aliasTypes;
        this.activities = activities;
        this.methods = methods;

        this.appliesToAllAliases = aliasTypes.contains(ALL);
        this.appliesToAllActivities = activities.contains(ALL);
        this.appliesToAllMethods = methods.contains(ALL);
    }

    @Override
    public LockoutState calculateLockoutState(final LockoutStateRequest request) {
        final VerificationAttempts applicableAttempts = getApplicableAttempts(request.getAttempts());
        final LockoutStateRequest updatedRequest = LockoutStateRequest.builder()
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
    public boolean appliesTo(final VerificationAttempt attempt) {
        log.info("checking that attempt {} applies to policy {}", attempt, this);
        final boolean applies = appliesToAlias(attempt.getAliasTypeName()) &&
                appliesToActivity(attempt.getActivityType());

        final Optional<String> methodName = attempt.getMethodName();
        return methodName.map(s -> applies && appliesToMethod(s)).orElse(applies);
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

    private boolean appliesToMethod(final String methodName) {
        final boolean appliesToMethod = appliesToAllMethods || methods.contains(methodName);
        if (appliesToMethod) {
            log.info("method {} applies to policy {}", methodName, this);
        } else {
            log.info("method {} does not apply to policy {}", methodName, this);
        }
        return appliesToMethod;
    }

}
