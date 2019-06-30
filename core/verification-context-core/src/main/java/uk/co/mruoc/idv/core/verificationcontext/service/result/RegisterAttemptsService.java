package uk.co.mruoc.idv.core.verificationcontext.service.result;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
/*import uk.co.mruoc.idv.core.lockoutstate.service.LoadLockoutStateService;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.RegisterAttemptsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;*/

@Builder
@Slf4j
public class RegisterAttemptsService {

    /*private final SequenceExtractor sequenceExtractor;
    private final VerificationMethodResultConverter converter;
    private final LoadLockoutStateService lockoutStateService;

    public void registerAttempts(final RegisterAttemptsRequest request) {
        final VerificationMethodResults newResults = request.getNewResults();
        newResults.stream().forEach(newResult -> handleResult(request, newResult));
    }

    private void handleResult(final RegisterAttemptsRequest request, final VerificationMethodResult newResult) {
        if (shouldRegisterAttempt(request, newResult)) {
            registerAttempt(request.getContext(), newResult);
        }
    }

    private boolean shouldRegisterAttempt(final RegisterAttemptsRequest request, final VerificationMethodResult newResult) {
        final VerificationContext context = request.getContext();
        final VerificationMethodSequence sequence = sequenceExtractor.extractSequence(context, newResult);
        if (sequence.shouldRegisterAttemptImmediately()) {
            log.info("registering attempt for result {} as sequence {} should fail immediately", newResult, sequence);
            return true;
        }
        final VerificationMethodResults existingResults = request.getExistingResults();
        final VerificationMethodResults allResults = existingResults.add(newResult);
        if (sequence.isComplete(allResults)) {
            log.info("registering attempt for result {} as sequence {} is complete", newResult, sequence);
            return true;
        }

        log.info("not registering attempt for result {} and sequence {}", newResult, sequence);
        return false;
    }

    private void registerAttempt(final VerificationContext context, final VerificationMethodResult result) {
        final VerificationAttempt attempt = converter.toAttempt(context, result);
        log.info("registering attempt {} for context {}", attempt, context);
        lockoutStateService.register(attempt);
    }*/

}
