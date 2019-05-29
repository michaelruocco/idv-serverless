package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;

import java.time.Instant;
import java.util.UUID;

@Builder
@Slf4j
public class GetVerificationContextService {

    private final VerificationContextDao dao;
    private final TimeService timeService;

    public VerificationContext load(final UUID id) {
        final VerificationContext context = dao.load(id).orElseThrow(() -> new VerificationContextNotFoundException(id));
        validate(context);
        return context;
    }

    private void validate(final VerificationContext context) {
        final Instant now = timeService.now();
        if (context.getExpiry().isBefore(now)) {
            log.info("context {} expired at {} current time is {}", context.getId(), context.getExpiry(), now);
            throw new VerificationContextExpiredException(context.getId(), context.getExpiry());
        }
    }

    public static class VerificationContextNotFoundException extends RuntimeException {

        public VerificationContextNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

    public static class VerificationContextExpiredException extends RuntimeException {

        private final Instant expiry;

        public VerificationContextExpiredException(final UUID id, final Instant expiry) {
            super(id.toString());
            this.expiry = expiry;
        }

        public Instant getExpiry() {
            return expiry;
        }

    }

}
