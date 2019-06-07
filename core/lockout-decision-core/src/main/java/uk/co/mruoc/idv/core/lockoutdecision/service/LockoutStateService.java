package uk.co.mruoc.idv.core.lockoutdecision.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.Collection;

@Slf4j
@Builder
public class LockoutStateService {

    private final VerificationAttemptsConverter converter;
    private final LockoutPoliciesService policiesService;
    private final LoadVerificationAttemptsService loadAttemptsService;
    private final VerificationAttemptsDao dao;

    public LockoutState register(final Collection<VerificationAttempt> attempts) {
        validate(attempts);
        LockoutState state = null;
        for (final VerificationAttempt attempt : attempts) {
            state = register(attempt);
        }
        return state;
    }

    public LockoutState register(final VerificationAttempt attempt) {
        log.info("registering attempt {}", attempt);
        final LockoutPolicy policy = loadPolicy(attempt);
        final Alias alias = attempt.getAlias();
        final VerificationAttempts attempts = loadAttempts(alias);

        final LockoutState state = calculateLockoutState(policy, attempts);
        if (state.isLocked()) {
            log.info("not registering attempt as lockout state is already locked");
            return state;
        }

        if (attempt.isSuccessful()) {
            return reset(policy, attempts);
        }

        return update(policy, attempts, attempt);
    }

    public LockoutState load(final LockoutStateRequest request) {
        log.info("loading lockout state for request {}", request);
        final LockoutPolicy policy = loadPolicy(request);
        final VerificationAttempts attempts = loadAttempts(request.getAlias());
        return calculateLockoutState(policy, attempts);
    }

    public LockoutState reset(final LockoutStateRequest request) {
        log.info("resetting lockout state for request {}", request);
        final LockoutPolicy policy = loadPolicy(request);
        final VerificationAttempts attempts = loadAttempts(request.getAlias());
        return reset(policy, attempts);
    }

    private VerificationAttempts loadAttempts(final Alias alias) {
        return loadAttemptsService.load(alias);
    }

    private LockoutPolicy loadPolicy(final LockoutStateRequest request) {
        final ChannelLockoutPolicies policies = policiesService.getPoliciesForChannel(request.getChannelId());
        return policies.getPolicyFor(request);
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

    private LockoutState calculateLockoutState(final LockoutPolicy policy, final VerificationAttempts attempts) {
        final CalculateLockoutStateRequest request = converter.toRequest(attempts);
        return policy.calculateLockoutState(request);
    }

    private void validate(final Collection<VerificationAttempt> attempts) {
        if (attempts.isEmpty()) {
            throw new IllegalArgumentException("attempts collection must not be empty");
        }
    }

}
