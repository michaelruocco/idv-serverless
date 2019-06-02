package uk.co.mruoc.idv.core.verificationcontext.service.result;

import org.junit.Test;
import org.mockito.InOrder;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.RegisterAttemptsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class RegisterAttemptsServiceTest {

    private final SequenceExtractor sequenceExtractor = mock(SequenceExtractor.class);
    private final VerificationMethodResultConverter converter = mock(VerificationMethodResultConverter.class);
    private final LockoutStateService lockoutStateService = mock(LockoutStateService.class);

    private final RegisterAttemptsService service = RegisterAttemptsService.builder()
            .sequenceExtractor(sequenceExtractor)
            .converter(converter)
            .lockoutStateService(lockoutStateService)
            .build();

    @Test
    public void shouldRegisterSuccessfulAttemptIfSequenceShouldFailImmediately() {
        final VerificationMethodResult newResult = mock(VerificationMethodResult.class);
        final VerificationMethodResults newResults = mock(VerificationMethodResults.class);
        final RegisterAttemptsRequest request = mock(RegisterAttemptsRequest.class);
        given(newResult.isSuccessful()).willReturn(true);
        given(newResults.stream()).willReturn(Stream.of(newResult));
        given(request.getNewResults()).willReturn(newResults);
        final VerificationContext context = mock(VerificationContext.class);
        given(request.getContext()).willReturn(context);
        final VerificationMethodSequence sequence = mock(VerificationMethodSequence.class);
        given(sequence.shouldRegisterAttemptImmediately()).willReturn(true);
        given(sequenceExtractor.extractSequence(context, newResult)).willReturn(sequence);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(converter.toAttempt(context, newResult)).willReturn(attempt);

        service.registerAttempts(request);

        verify(lockoutStateService).register(attempt);
    }

    @Test
    public void shouldRegisterMultipleSuccessfulAttemptsIfSequenceShouldFailImmediately() {
        final VerificationMethodResult newResult1 = mock(VerificationMethodResult.class);
        final VerificationMethodResult newResult2 = mock(VerificationMethodResult.class);
        final VerificationMethodResults newResults = mock(VerificationMethodResults.class);
        final RegisterAttemptsRequest request = mock(RegisterAttemptsRequest.class);
        given(newResult1.isSuccessful()).willReturn(true);
        given(newResult2.isSuccessful()).willReturn(true);
        given(newResults.stream()).willReturn(Stream.of(newResult1, newResult2));
        given(request.getNewResults()).willReturn(newResults);
        final VerificationContext context = mock(VerificationContext.class);
        given(request.getContext()).willReturn(context);
        final VerificationMethodSequence sequence = mock(VerificationMethodSequence.class);
        given(sequence.shouldRegisterAttemptImmediately()).willReturn(true);
        given(sequenceExtractor.extractSequence(context, newResult1)).willReturn(sequence);
        given(sequenceExtractor.extractSequence(context, newResult2)).willReturn(sequence);
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        given(converter.toAttempt(context, newResult1)).willReturn(attempt1);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        given(converter.toAttempt(context, newResult2)).willReturn(attempt2);

        service.registerAttempts(request);

        final InOrder inOrder = inOrder(lockoutStateService);
        inOrder.verify(lockoutStateService).register(attempt1);
        inOrder.verify(lockoutStateService).register(attempt2);
    }

    @Test
    public void shouldRegisterFailureAttemptIfSequenceShouldFailImmediately() {
        final VerificationMethodResult newResult = mock(VerificationMethodResult.class);
        final VerificationMethodResults newResults = mock(VerificationMethodResults.class);
        final RegisterAttemptsRequest request = mock(RegisterAttemptsRequest.class);
        given(newResult.isSuccessful()).willReturn(false);
        given(newResults.stream()).willReturn(Stream.of(newResult));
        given(request.getNewResults()).willReturn(newResults);
        final VerificationContext context = mock(VerificationContext.class);
        given(request.getContext()).willReturn(context);
        final VerificationMethodSequence sequence = mock(VerificationMethodSequence.class);
        given(sequence.shouldRegisterAttemptImmediately()).willReturn(true);
        given(sequenceExtractor.extractSequence(context, newResult)).willReturn(sequence);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(converter.toAttempt(context, newResult)).willReturn(attempt);

        service.registerAttempts(request);

        verify(lockoutStateService).register(attempt);
    }

    @Test
    public void shouldNotRegisterFailureAttemptIfSequenceShouldFailOnCompletionAndSequenceIsNotComplete() {
        final VerificationMethodResult newResult = mock(VerificationMethodResult.class);
        final VerificationMethodResults newResults = mock(VerificationMethodResults.class);
        final VerificationMethodResults existingResults = mock(VerificationMethodResults.class);
        final RegisterAttemptsRequest request = mock(RegisterAttemptsRequest.class);
        given(newResult.isSuccessful()).willReturn(false);
        given(newResults.stream()).willReturn(Stream.of(newResult));
        given(request.getNewResults()).willReturn(newResults);
        given(request.getExistingResults()).willReturn(existingResults);
        final VerificationContext context = mock(VerificationContext.class);
        given(request.getContext()).willReturn(context);
        final VerificationMethodSequence sequence = mock(VerificationMethodSequence.class);
        given(sequence.shouldRegisterAttemptImmediately()).willReturn(false);
        final VerificationMethodResults allResults = mock(VerificationMethodResults.class);
        given(existingResults.add(newResult)).willReturn(allResults);
        given(sequence.isComplete(allResults)).willReturn(false);
        given(sequenceExtractor.extractSequence(context, newResult)).willReturn(sequence);

        service.registerAttempts(request);

        verify(lockoutStateService, never()).register(any(VerificationAttempt.class));
    }

    @Test
    public void shouldRegisterFailureAttemptIfSequenceShouldFailOnCompletionAndSequenceIsComplete() {
        final VerificationMethodResult newResult = mock(VerificationMethodResult.class);
        final VerificationMethodResults newResults = mock(VerificationMethodResults.class);
        final VerificationMethodResults existingResults = mock(VerificationMethodResults.class);
        final RegisterAttemptsRequest request = mock(RegisterAttemptsRequest.class);
        given(newResult.isSuccessful()).willReturn(false);
        given(newResults.stream()).willReturn(Stream.of(newResult));
        given(request.getNewResults()).willReturn(newResults);
        given(request.getExistingResults()).willReturn(existingResults);
        final VerificationContext context = mock(VerificationContext.class);
        given(request.getContext()).willReturn(context);
        final VerificationMethodSequence sequence = mock(VerificationMethodSequence.class);
        given(sequence.shouldRegisterAttemptImmediately()).willReturn(false);
        final VerificationMethodResults allResults = mock(VerificationMethodResults.class);
        given(existingResults.add(newResult)).willReturn(allResults);
        given(sequence.isComplete(allResults)).willReturn(true);
        given(sequenceExtractor.extractSequence(context, newResult)).willReturn(sequence);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(converter.toAttempt(context, newResult)).willReturn(attempt);

        service.registerAttempts(request);

        verify(lockoutStateService).register(attempt);
    }

}