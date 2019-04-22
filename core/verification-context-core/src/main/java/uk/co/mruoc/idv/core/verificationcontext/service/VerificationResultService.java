package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Builder;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.util.Optional;
import java.util.UUID;

@Builder
public class VerificationResultService {

    private final VerificationResultsDao dao;
    private final LoadVerificationContextService loadContextService;

    public void save(final VerificationMethodResult result) {
        validateResult(result);
        final UUID contextId = result.getVerificationContextId();
        final VerificationMethodResults results = loadResults(contextId);
        final VerificationMethodResults updatedResults = results.add(result);
        dao.save(updatedResults);
    }

    public VerificationMethodResults loadResults(final UUID contextId) {
        return dao.load(contextId).orElse(createNewResults(contextId));
    }

    private void validateResult(final VerificationMethodResult result) {
        final UUID contextId = result.getVerificationContextId();
        final VerificationContext context = loadContextService.load(contextId);
        final String sequenceName = result.getSequenceName();
        final Optional<VerificationMethodSequence> sequence = context.getSequence(sequenceName);
        if (!sequence.isPresent()) {
            throw new SequenceNotFoundException(contextId, sequenceName);
        }
        final String methodName = result.getMethodName();
        if (!sequence.get().containsMethod(methodName)) {
            throw new MethodNotFoundInSequenceException(contextId, sequenceName, methodName);
        }
    }

    private VerificationMethodResults createNewResults(final UUID contextId) {
        return VerificationMethodResults.builder()
                .verificationContextId(contextId)
                .build();
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

    public static class MethodNotFoundInSequenceException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "method %s not found in sequence %s in verification context %s";

        private final UUID contextId;
        private final String sequenceName;
        private final String methodName;

        public MethodNotFoundInSequenceException(final UUID contextId, final String sequenceName, final String methodName) {
            super(String.format(MESSAGE_FORMAT, methodName, sequenceName, contextId));
            this.contextId = contextId;
            this.sequenceName = sequenceName;
            this.methodName = methodName;
        }

        public UUID getContextId() {
            return contextId;
        }

        public String getSequenceName() {
            return sequenceName;
        }

        public String getMethodName() {
            return methodName;
        }

    }

}
