package uk.co.mruoc.idv.core.lockoutstate.service;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutstate.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

public class VerificationAttemptsConverter {

    private final TimeService timeService;

    public VerificationAttemptsConverter(final TimeService timeService) {
        this.timeService = timeService;
    }

    public CalculateLockoutStateRequest toRequest(final Alias alias, final VerificationAttempts attempts) {
        return CalculateLockoutStateRequest.builder()
                .alias(alias)
                .attempts(attempts)
                .timestamp(timeService.now())
                .build();
    }

}
