package uk.co.mruoc.idv.core.lockoutdecision.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

@Slf4j
@Builder
public class LockoutStateService {

    private final VerificationAttemptsConverter converter;
    private final LockoutPoliciesService policiesService;
    private final LoadVerificationAttemptsService loadAttemptsService;
    private final VerificationAttemptsDao dao;

    public LockoutState register(final VerificationAttempt attempt) {
        log.info("registering attempt {}", attempt);
        final LockoutPolicy policy = loadPolicy(attempt);
        final VerificationAttempts attempts = loadAttemptsService.load(attempt.getAlias());

        final LockoutState initialLockoutState = calculateLockoutState(policy, attempts);
        if (initialLockoutState.isLocked()) {
            log.info("not registering attempt as lockout state is already locked");
            return initialLockoutState;
        }

        if (attempt.isSuccessful()) {
            return reset(policy, attempts);
        }

        return update(policy, attempts, attempt);
    }

    public LockoutState load(final VerificationAttempt attempt) {
        log.info("loading lockout state for attempt {}", attempt);
        final LockoutPolicy policy = loadPolicy(attempt);
        final VerificationAttempts attempts = loadAttemptsService.load(attempt.getAlias());
        return calculateLockoutState(policy, attempts);
    }

    private LockoutState calculateLockoutState(final LockoutPolicy policy, final VerificationAttempts attempts) {
        final LockoutStateRequest request = converter.toRequest(attempts);
        return policy.calculateLockoutState(request);
    }

    private LockoutPolicy loadPolicy(final VerificationAttempt attempt) {
        final ChannelLockoutPolicies policies = policiesService.getPoliciesForChannel(attempt.getChannelId());
        return policies.getPolicyFor(attempt);
    }

    private LockoutState reset(final LockoutPolicy policy, final VerificationAttempts attempts) {
        log.info("resetting verification attempts");
        final VerificationAttempts resetAttempts = policy.reset(attempts);
        return update(policy, resetAttempts);
    }

    private LockoutState update(final LockoutPolicy policy, final VerificationAttempts attempts, final VerificationAttempt attempt) {
        log.info("updating verification attempts");
        final VerificationAttempts updatedAttempts = attempts.add(attempt);
        return update(policy, updatedAttempts);
    }

    private LockoutState update(final LockoutPolicy policy, final VerificationAttempts attempts) {
        log.info("persisting verification attempts {}", attempts);
        dao.save(attempts);
        final LockoutState state = calculateLockoutState(policy, attempts);
        log.info("calculated lockout state {}", state);
        return state;
    }

}
