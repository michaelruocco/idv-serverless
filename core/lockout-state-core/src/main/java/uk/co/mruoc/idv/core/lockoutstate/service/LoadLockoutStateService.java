package uk.co.mruoc.idv.core.lockoutstate.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;

@Slf4j
@Builder
public class LoadLockoutStateService {

    private final LockoutPoliciesService policiesService;
    private final VerificationAttemptsService attemptsService;
    private final LockoutStateCalculationService calculationService;

    public LockoutState load(final LockoutStateRequest request) {
        log.info("loading lockout state for request {}", request);
        final LockoutPolicy policy = policiesService.getPolicy(request);
        final VerificationAttempts attempts = attemptsService.load(request.getAlias());
        return calculationService.calculateLockoutState(request.getAlias(), policy, attempts);
    }

}
