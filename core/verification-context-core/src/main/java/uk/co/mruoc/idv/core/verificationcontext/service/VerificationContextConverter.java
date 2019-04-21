package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.event.VerificationContextCreatedEvent;

public class VerificationContextConverter {

    private final TimeService timeService;

    public VerificationContextConverter(final TimeService timeService) {
        this.timeService = timeService;
    }

    public VerificationContextCreatedEvent toCreatedEvent(final VerificationContext context) {
        return VerificationContextCreatedEvent.builder()
                .timestamp(timeService.now())
                .context(context)
                .build();
    }

}
