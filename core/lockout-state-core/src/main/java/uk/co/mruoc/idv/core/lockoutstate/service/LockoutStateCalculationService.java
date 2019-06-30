package uk.co.mruoc.idv.core.lockoutstate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutstate.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

@Slf4j
@RequiredArgsConstructor
public class LockoutStateCalculationService {

    private final VerificationAttemptsConverter converter;

    public LockoutState calculateLockoutState(final Alias alias, final LockoutPolicy policy, final VerificationAttempts attempts) {
        final CalculateLockoutStateRequest request = converter.toRequest(alias, attempts);
        return policy.calculateLockoutState(request);
    }

}
