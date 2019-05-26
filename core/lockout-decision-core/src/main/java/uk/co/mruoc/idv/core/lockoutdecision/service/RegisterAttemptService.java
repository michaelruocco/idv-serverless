package uk.co.mruoc.idv.core.lockoutdecision.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.ChannelLockoutPolicies;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.service.TimeService;

import java.util.UUID;

@Slf4j
@Builder
public class RegisterAttemptService {

    private final TimeService timeService;
    private final LockoutPoliciesService policiesService;
    private final IdentityService identityService;
    private final VerificationAttemptsDao dao;

    public LockoutState registerAttempt(final VerificationAttempt attempt) {
        log.info("registering attempt {}", attempt);
        final LockoutPolicy policy = loadPolicy(attempt);
        final VerificationAttempts attempts = loadAttempts(attempt);
        final LockoutState initialLockoutState = loadLockoutState(policy, attempts);
        if (initialLockoutState.isLocked()) {
            return initialLockoutState;
        }

        if (attempt.isSuccessful()) {
            return resetLockoutState(attempts, policy);
        }

        update(attempts, attempt);
        return loadLockoutState(policy, attempts);
    }

    private void update(final VerificationAttempts attempts, final VerificationAttempt attempt) {
        attempts.add(attempt);
        dao.save(attempts);
    }

    private LockoutState loadLockoutState(final LockoutPolicy policy, final VerificationAttempts attempts) {
        final LockoutStateRequest initialStateRequest = toRequest(attempts);
        return policy.calculateLockoutState(initialStateRequest);
    }

    private LockoutPolicy loadPolicy(final VerificationAttempt attempt) {
        final ChannelLockoutPolicies policies = policiesService.getPoliciesForChannel(attempt.getChannelId());
        return policies.getPolicyFor(attempt);
    }

    private LockoutState resetLockoutState(final VerificationAttempts attempts, final LockoutPolicy policy) {
        final VerificationAttempts resetAttempts = policy.reset(attempts);
        dao.save(resetAttempts);
        final LockoutStateRequest request = toRequest(attempts);
        return policy.calculateLockoutState(request);
    }

    private VerificationAttempts loadAttempts(final VerificationAttempt attempt) {
        final UUID idvId = loadIdvId(attempt.getAlias());
        return dao.loadByIdvId(idvId);
    }

    private UUID loadIdvId(final Alias alias) {
        if (AliasType.isIdvId(alias.getTypeName())) {
            return ((IdvIdAlias) alias).getValueAsUuid();
        }
        final Identity identity = identityService.load(alias);
        return identity.getIdvId();
    }

    private LockoutStateRequest toRequest(final VerificationAttempts attempts) {
        return LockoutStateRequest.builder()
                .attempts(attempts)
                .timestamp(timeService.now())
                .build();
    }

}
