package uk.co.mruoc.idv.core.verificationcontext.service.result;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;

import java.util.Optional;
import java.util.UUID;

public class SequenceExtractor {

    public VerificationMethodSequence extractSequence(final VerificationContext context, final VerificationMethodResult result) {
        return extractSequence(context, result.getSequenceName());
    }

    public VerificationMethodSequence extractSequence(final VerificationContext context, final String sequenceName) {
        final Optional<VerificationMethodSequence> sequence = context.getSequence(sequenceName);
        return sequence.orElseThrow(() -> new SequenceNotFoundException(context.getId(), sequenceName));
    }

    public static class SequenceNotFoundException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "sequence %s not found in verification context %s";

        private final UUID contextId;
        private final String sequenceName;

        public SequenceNotFoundException(final UUID contextId, final String sequenceName) {
            super(String.format(MESSAGE_FORMAT, sequenceName, contextId));
            this.contextId = contextId;
            this.sequenceName = sequenceName;
        }

        public UUID getContextId() {
            return contextId;
        }

        public String getSequenceName() {
            return sequenceName;
        }

    }

}
