package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.service.LoadVerificationContextService.VerificationContextNotFoundException;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultService.MethodNotFoundInSequenceException;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultService.SequenceNotFoundException;

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
    private final LoadVerificationContextService loadContextService = mock(LoadVerificationContextService.class);
    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);

    private final VerificationResultService service = VerificationResultService.builder()
            .dao(dao)
            .loadContextService(loadContextService)
            .uuidGenerator(uuidGenerator)
            .build();

    @Test
    public void shouldReturnEmptyResultsIfResultsNotFound() {
        final UUID contextId = UUID.randomUUID();
        given(dao.load(contextId)).willReturn(Optional.empty());
        final UUID id = UUID.randomUUID();
        given(uuidGenerator.randomUuid()).willReturn(id);

        final VerificationMethodResults results = service.loadResults(contextId);

        assertThat(results.getId()).isEqualTo(id);
        assertThat(results.getContextId()).isEqualTo(contextId);
        assertThat(results).isEmpty();
    }

    @Test
    public void shouldReturnResultsIfResultsFound() {
        final UUID contextId = UUID.randomUUID();
        final VerificationMethodResults expectedResults = mock(VerificationMethodResults.class);
        given(dao.load(contextId)).willReturn(Optional.of(expectedResults));

        final VerificationMethodResults actualResults = service.loadResults(contextId);

        assertThat(actualResults).isEqualTo(expectedResults);
    }

    @Test
    public void shouldThrowExceptionIfContextNotFound() {
        final UUID contextId = UUID.randomUUID();
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .build();
        doThrow(VerificationContextNotFoundException.class).when(loadContextService).load(contextId);

        final Throwable cause = catchThrowable(() -> service.save(result));

        assertThat(cause).isInstanceOf(VerificationContextNotFoundException.class);
    }

    @Test
    public void shouldThrowExceptionIfContextFoundButDoesNotContainSequence() {
        final UUID contextId = UUID.fromString("786f80d3-f148-413d-bdef-4847fd3c637b");
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName("sequenceName")
                .build();
        final VerificationMethodSequence sequence = new VerificationMethodSequence("otherSequenceName", Collections.emptyList());
        final VerificationContext context = VerificationContext.builder()
                .sequences(Collections.singleton(sequence))
                .build();
        given(loadContextService.load(contextId)).willReturn(context);

        final Throwable cause = catchThrowable(() -> service.save(result));

        assertThat(cause).isInstanceOf(SequenceNotFoundException.class)
                .hasMessage("sequence sequenceName not found in verification context 786f80d3-f148-413d-bdef-4847fd3c637b");
    }

    @Test
    public void shouldThrowExceptionIfContextFoundWithSequenceButSequenceDoesNotContainMethod() {
        final UUID contextId = UUID.fromString("786f80d3-f148-413d-bdef-4847fd3c637b");
        final String sequenceName = "sequenceName";
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName(sequenceName)
                .methodName("methodName")
                .build();
        final VerificationMethod method = new DefaultVerificationMethod("otherMethodName");
        final VerificationMethodSequence sequence = new VerificationMethodSequence(sequenceName, Collections.singleton(method));
        final VerificationContext context = VerificationContext.builder()
                .sequences(Collections.singleton(sequence))
                .build();
        given(loadContextService.load(contextId)).willReturn(context);

        final Throwable cause = catchThrowable(() -> service.save(result));

        assertThat(cause).isInstanceOf(MethodNotFoundInSequenceException.class)
                .hasMessage("method methodName not found in sequence sequenceName in verification context 786f80d3-f148-413d-bdef-4847fd3c637b");
    }

    @Test
    public void shouldCreateNewResultsAndAddResultIfResultsDoNotExist() {
        final UUID contextId = UUID.fromString("786f80d3-f148-413d-bdef-4847fd3c637b");
        final String sequenceName = "sequenceName";
        final String methodName = "methodName";
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName(sequenceName)
                .methodName(methodName)
                .build();
        final VerificationMethod method = new DefaultVerificationMethod(methodName);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(sequenceName, Collections.singleton(method));
        final VerificationContext context = VerificationContext.builder()
                .sequences(Collections.singleton(sequence))
                .build();
        given(loadContextService.load(contextId)).willReturn(context);
        given(dao.load(contextId)).willReturn(Optional.empty());
        final UUID id = UUID.randomUUID();
        given(uuidGenerator.randomUuid()).willReturn(id);

        service.save(result);

        final ArgumentCaptor<VerificationMethodResults> captor = ArgumentCaptor.forClass(VerificationMethodResults.class);
        verify(dao).save(captor.capture());
        final VerificationMethodResults results = captor.getValue();
        assertThat(results.getId()).isEqualTo(id);
        assertThat(results.getContextId()).isEqualTo(contextId);
        assertThat(results).containsExactly(result);
    }

    @Test
    public void shouldAddResultIfResultsIfResultsAlreadyExist() {
        final UUID contextId = UUID.fromString("786f80d3-f148-413d-bdef-4847fd3c637b");
        final String sequenceName = "sequenceName";
        final String methodName = "methodName";
        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .sequenceName(sequenceName)
                .methodName(methodName)
                .build();
        final VerificationMethod method = new DefaultVerificationMethod(methodName);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(sequenceName, Collections.singleton(method));
        final VerificationContext context = VerificationContext.builder()
                .sequences(Collections.singleton(sequence))
                .build();
        given(loadContextService.load(contextId)).willReturn(context);
        final VerificationMethodResult existingResult = mock(VerificationMethodResult.class);
        final VerificationMethodResults existingResults = VerificationMethodResults.builder()
                .contextId(contextId)
                .results(Collections.singleton(existingResult))
                .build();
        given(dao.load(contextId)).willReturn(Optional.of(existingResults));

        service.save(result);

        final ArgumentCaptor<VerificationMethodResults> captor = ArgumentCaptor.forClass(VerificationMethodResults.class);
        verify(dao).save(captor.capture());
        final VerificationMethodResults results = captor.getValue();
        assertThat(results.getContextId()).isEqualTo(contextId);
        assertThat(results).containsExactly(existingResult, result);
    }

}
