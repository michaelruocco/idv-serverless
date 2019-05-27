package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Builder;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import java.util.UUID;

@Builder
public class GetVerificationContextService {

    private final VerificationContextDao dao;

    public VerificationContext load(final UUID id) {
        return dao.load(id).orElseThrow(() -> new VerificationContextNotFoundException(id));
    }

    public static class VerificationContextNotFoundException extends RuntimeException {

        public VerificationContextNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

}
