package uk.co.mruoc.idv.core.verificationcontext.service.result;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.RegisterAttemptsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class VerificationResultService {

    private final VerificationResultsDao dao;
    private final GetVerificationContextService getContextService;
    private final UuidGenerator uuidGenerator;
    private final SequenceExtractor sequenceExtractor;
    private final RegisterAttemptsService registerAttemptsService;

    public VerificationMethodResults upsert(final VerificationMethodResults results) {
        log.info("upserting results {}", results);
        final UUID contextId = results.getContextId();
        final VerificationContext context = loadContext(contextId);
        final VerificationMethodResults loadedResults = loadOrCreate(contextId);
        validateResults(results, context, loadedResults);

        registerAttempts(results, context, loadedResults);

        final VerificationMethodResults updatedResults = loadedResults.addAll(results);
        persist(updatedResults);
        return updatedResults;
    }

    public Optional<VerificationMethodResults> load(final UUID contextId) {
        return dao.load(contextId);
    }

    private VerificationContext loadContext(final UUID contextId) {
        log.info("loading context with id {}", contextId);
        return getContextService.load(contextId);
    }

    private VerificationMethodResults loadOrCreate(final UUID contextId) {
        return dao.load(contextId).orElseGet(() -> createNewResults(contextId));
    }

    private void validateResults(final VerificationMethodResults results, final VerificationContext context, final VerificationMethodResults existingResults) {
        results.stream().forEach(result -> validateResult(result, context, existingResults));
    }

    private void validateResult(final VerificationMethodResult result, final VerificationContext context, final VerificationMethodResults existingResults) {
        log.info("validating result {} against context {} with existing results {}", result, context, existingResults);
        final UUID contextId = context.getId();
        final String sequenceName = result.getSequenceName();
        final VerificationMethodSequence sequence = sequenceExtractor.extractSequence(context, sequenceName);
        if (sequence.isComplete(existingResults)) {
            throw new SequenceAlreadyCompleteException(contextId, sequenceName);
        }
        final String methodName = result.getMethodName();
        if (!sequence.containsMethod(methodName)) {
            throw new MethodNotFoundInSequenceException(contextId, sequenceName, methodName);
        }
    }

    private VerificationMethodResults createNewResults(final UUID contextId) {
        return VerificationMethodResults.builder()
                .id(uuidGenerator.randomUuid())
                .contextId(contextId)
                .build();
    }

    private void persist(final VerificationMethodResults results) {
        log.info("updated results {}", results);
        dao.save(results);
    }

    private void registerAttempts(final VerificationMethodResults newResults,
                                  final VerificationContext context,
                                  final VerificationMethodResults existingResults) {
        final RegisterAttemptsRequest request = RegisterAttemptsRequest.builder()
                .newResults(newResults)
                .context(context)
                .existingResults(existingResults)
                .build();
        registerAttemptsService.registerAttempts(request);
    }

    public static class SequenceAlreadyCompleteException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "sequence %s in verification context %s is already complete";

        private final UUID contextId;
        private final String sequenceName;

        public SequenceAlreadyCompleteException(final UUID contextId, final String sequenceName) {
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
