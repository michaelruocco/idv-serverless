package uk.co.mruoc.idv.core.verificationcontext.service.result;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.RegisterAttemptsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService.VerificationContextNotFoundException;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService.MethodNotFoundInSequenceException;
import uk.co.mruoc.idv.core.verificationcontext.service.result.SequenceExtractor.SequenceNotFoundException;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService.SequenceAlreadyCompleteException;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VerificationResultServiceTest {

    private final VerificationResultsDao dao = mock(VerificationResultsDao.class);
    private final GetVerificationContextService getContextService = mock(GetVerificationContextService.class);
    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);
    private final SequenceExtractor sequenceExtractor = mock(SequenceExtractor.class);
    private final RegisterAttemptsService registerAttemptsService = mock(RegisterAttemptsService.class);

    private final VerificationResultService service = VerificationResultService.builder()
            .dao(dao)
            .getContextService(getContextService)
            .uuidGenerator(uuidGenerator)
            .sequenceExtractor(sequenceExtractor)
            .registerAttemptsService(registerAttemptsService)
            .build();

    @Test
    public void shouldReturnEmptyOptionalIfResultsNotFound() {
        final UUID contextId = UUID.randomUUID();
        given(dao.load(contextId)).willReturn(Optional.empty());

        final Optional<VerificationMethodResults> results = service.load(contextId);

        assertThat(results).isEmpty();
    }

    @Test
    public void shouldReturnResultsIfResultsFound() {
        final UUID contextId = UUID.randomUUID();
        final VerificationMethodResults expectedResults = mock(VerificationMethodResults.class);
        given(dao.load(contextId)).willReturn(Optional.of(expectedResults));

        final Optional<VerificationMethodResults> actualResults = service.load(contextId);

        assertThat(actualResults).contains(expectedResults);
    }

    @Test
    public void shouldThrowExceptionIfContextNotFound() {
        final UUID contextId = UUID.randomUUID();
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName("sequenceName")
                .build();
        final VerificationMethodResults results = toResults(result);
        doThrow(VerificationContextNotFoundException.class).when(getContextService).load(contextId);

        final Throwable cause = catchThrowable(() -> service.upsert(results));

        assertThat(cause).isInstanceOf(VerificationContextNotFoundException.class);
    }

    @Test
    public void shouldThrowExceptionIfContextFoundButDoesNotContainSequence() {
        final UUID contextId = UUID.fromString("786f80d3-f148-413d-bdef-4847fd3c637b");
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName("sequenceName")
                .build();
        final VerificationMethodResults results = toResults(result);
        final VerificationMethodSequence sequence = mock(VerificationMethodSequence.class);
        final VerificationContext context = VerificationContext.builder()
                .id(contextId)
                .sequences(Collections.singleton(sequence))
                .build();
        given(getContextService.load(contextId)).willReturn(context);
        given(dao.load(contextId)).willReturn(Optional.empty());
        doThrow(SequenceNotFoundException.class).when(sequenceExtractor).extractSequence(context, result.getSequenceName());

        final Throwable cause = catchThrowable(() -> service.upsert(results));

        assertThat(cause).isInstanceOf(SequenceNotFoundException.class);
    }

    @Test
    public void shouldThrowExceptionIfContextFoundWithSequenceButSequenceDoesNotContainMethod() {
        final UUID contextId = UUID.randomUUID();
        final String sequenceName = "sequenceName";
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName(sequenceName)
                .methodName("methodName")
                .build();
        final VerificationMethodResults results = toResults(result);
        final VerificationMethod method = new DefaultVerificationMethod("otherMethodName");
        final VerificationMethodSequence sequence = new VerificationMethodSequence(sequenceName, Collections.singleton(method));
        final VerificationContext context = toContext(contextId, sequence);
        given(getContextService.load(contextId)).willReturn(context);
        given(dao.load(contextId)).willReturn(Optional.empty());
        given(sequenceExtractor.extractSequence(context, sequenceName)).willReturn(sequence);

        final Throwable cause = catchThrowable(() -> service.upsert(results));

        final String expectedMessage = String.format("method methodName not found in sequence sequenceName in verification context %s", contextId);
        assertThat(cause).isInstanceOf(MethodNotFoundInSequenceException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    public void shouldCreateNewResultsAndAddResultIfResultsDoNotExist() {
        final UUID contextId = UUID.randomUUID();
        final String sequenceName = "sequenceName";
        final String methodName = "methodName";
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName(sequenceName)
                .methodName(methodName)
                .build();
        final VerificationMethodResults results = toResults(result);
        final VerificationMethod method = new DefaultVerificationMethod(methodName);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(sequenceName, Collections.singleton(method));
        final VerificationContext context = toContext(contextId, sequence);
        given(getContextService.load(contextId)).willReturn(context);
        given(dao.load(contextId)).willReturn(Optional.empty());
        given(sequenceExtractor.extractSequence(context, sequenceName)).willReturn(sequence);
        final UUID id = UUID.randomUUID();
        given(uuidGenerator.randomUuid()).willReturn(id);

        service.upsert(results);

        final ArgumentCaptor<VerificationMethodResults> captor = ArgumentCaptor.forClass(VerificationMethodResults.class);
        verify(dao).save(captor.capture());
        final VerificationMethodResults actualResults = captor.getValue();
        assertThat(actualResults.getId()).isEqualTo(id);
        assertThat(actualResults.getContextId()).isEqualTo(contextId);
        assertThat(actualResults).containsExactly(result);
    }

    @Test
    public void shouldAddResultIfResultsAlreadyExist() {
        final UUID contextId = UUID.randomUUID();
        final String sequenceName = "sequenceName";
        final String methodName = "methodName";
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName(sequenceName)
                .methodName(methodName)
                .build();
        final VerificationMethodResults results = toResults(result);
        final VerificationMethod method = new DefaultVerificationMethod(methodName);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(sequenceName, Collections.singleton(method));
        final VerificationContext context = toContext(contextId, sequence);
        given(getContextService.load(contextId)).willReturn(context);
        final VerificationMethodResult existingResult = mock(VerificationMethodResult.class);
        given(existingResult.getSequenceName()).willReturn(sequenceName);
        final VerificationMethodResults existingResults = VerificationMethodResults.builder()
                .contextId(contextId)
                .results(Collections.singleton(existingResult))
                .build();
        given(dao.load(contextId)).willReturn(Optional.of(existingResults));
        given(sequenceExtractor.extractSequence(context, sequenceName)).willReturn(sequence);

        service.upsert(results);

        final ArgumentCaptor<VerificationMethodResults> captor = ArgumentCaptor.forClass(VerificationMethodResults.class);
        verify(dao).save(captor.capture());
        final VerificationMethodResults actualResults = captor.getValue();
        assertThat(actualResults.getContextId()).isEqualTo(contextId);
        assertThat(actualResults).containsExactly(existingResult, result);
    }

    @Test
    public void shouldThrowExceptionIfSequenceIsAlreadyComplete() {
        final UUID contextId = UUID.randomUUID();
        final String sequenceName = "sequenceName";
        final String methodName = "methodName";
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName(sequenceName)
                .methodName(methodName)
                .build();
        final VerificationMethodResults results = toResults(result);
        final VerificationMethodResult existingResult = mock(VerificationMethodResult.class);
        given(existingResult.getSequenceName()).willReturn(sequenceName);
        final VerificationMethodResults existingResults = VerificationMethodResults.builder()
                .contextId(contextId)
                .results(Collections.singleton(existingResult))
                .build();
        final VerificationMethodSequence sequence = mock(VerificationMethodSequence.class);
        given(sequence.isComplete(existingResults)).willReturn(true);
        given(dao.load(contextId)).willReturn(Optional.of(existingResults));
        final VerificationContext context = toContext(contextId, sequence);
        given(getContextService.load(contextId)).willReturn(context);
        given(sequenceExtractor.extractSequence(context, sequenceName)).willReturn(sequence);

        final Throwable cause = catchThrowable(() -> service.upsert(results));

        final String expectedMessage = String.format("sequence sequenceName in verification context %s is already complete", contextId);
        assertThat(cause).isInstanceOf(SequenceAlreadyCompleteException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    public void shouldRegisterAttempts() {
        final UUID contextId = UUID.randomUUID();
        final String sequenceName = "sequenceName";
        final String methodName = "methodName";
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName(sequenceName)
                .methodName(methodName)
                .build();
        final VerificationMethodResults results = toResults(result);
        final VerificationMethod method = new DefaultVerificationMethod(methodName);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(sequenceName, Collections.singleton(method));
        final VerificationContext context = toContext(contextId, sequence);
        given(getContextService.load(contextId)).willReturn(context);
        final VerificationMethodResult existingResult = mock(VerificationMethodResult.class);
        given(existingResult.getSequenceName()).willReturn(sequenceName);
        final VerificationMethodResults existingResults = VerificationMethodResults.builder()
                .contextId(contextId)
                .results(Collections.singleton(existingResult))
                .build();
        given(dao.load(contextId)).willReturn(Optional.of(existingResults));
        given(sequenceExtractor.extractSequence(context, sequenceName)).willReturn(sequence);

        service.upsert(results);

        final ArgumentCaptor<RegisterAttemptsRequest> captor = ArgumentCaptor.forClass(RegisterAttemptsRequest.class);
        verify(registerAttemptsService).registerAttempts(captor.capture());
        final RegisterAttemptsRequest request = captor.getValue();
        assertThat(request.getNewResults()).isEqualTo(results);
        assertThat(request.getContext()).isEqualTo(context);
        assertThat(request.getExistingResults()).isEqualTo(existingResults);
    }

    private static VerificationMethodResults toResults(final VerificationMethodResult result) {
        return VerificationMethodResults.builder()
                .contextId(result.getContextId())
                .results(Collections.singleton(result))
                .build();
    }

    private static VerificationContext toContext(final UUID contextId, final VerificationMethodSequence sequence) {
        return VerificationContext.builder()
                .id(contextId)
                .sequences(Collections.singleton(sequence))
                .build();
    }

}
