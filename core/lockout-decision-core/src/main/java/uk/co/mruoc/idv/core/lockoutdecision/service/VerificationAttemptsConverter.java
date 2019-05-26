package uk.co.mruoc.idv.core.lockoutdecision.service;

import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.service.TimeService;

public class VerificationAttemptsConverter {

    private final TimeService timeService;

    public VerificationAttemptsConverter(final TimeService timeService) {
        this.timeService = timeService;
    }

    public LockoutStateRequest toRequest(final VerificationAttempts attempts) {
        return LockoutStateRequest.builder()
                .attempts(attempts)
                .timestamp(timeService.now())
                .build();
    }

}
