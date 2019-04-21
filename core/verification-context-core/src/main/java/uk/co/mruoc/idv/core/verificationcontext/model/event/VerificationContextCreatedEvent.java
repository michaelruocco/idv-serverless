package uk.co.mruoc.idv.core.verificationcontext.model.event;

import lombok.Builder;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.events.DefaultEvent;

import java.time.Instant;

public class VerificationContextCreatedEvent extends DefaultEvent {

    private static final String TYPE = "VERIFICATION_CONTEXT_CREATED_EVENT";

    @Builder
    public VerificationContextCreatedEvent(final Instant timestamp, final VerificationContext context) {
        super(TYPE, timestamp, context);
    }

}
