package uk.co.mruoc.idv.core.lockoutdecision.service;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutdecision.model.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.service.TimeService;

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
