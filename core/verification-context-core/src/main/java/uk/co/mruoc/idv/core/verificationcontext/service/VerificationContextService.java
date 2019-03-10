package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Builder;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;

import java.time.Instant;

@Builder
public class VerificationContextService {

    private final UuidGenerator idGenerator;
    private final TimeService timeService;
    private final ExpiryCalculator expiryCalculator;
    private final VerificationContextDao dao;

    public VerificationContext create(final VerificationContextRequest request) {
        final Instant created = timeService.now();
        return VerificationContext.builder()
                .id(idGenerator.randomUuid())
                .channel(request.getChannel())
                .inputAlias(request.getInputAlias())
                .identity(request.getIdentity())
                .activity(request.getActivity())
                .created(created)
                .expiry(expiryCalculator.calculateExpiry(created))
                .build();
    }

}
