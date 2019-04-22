package uk.co.mruoc.idv.core.verificationcontext.model.result;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationMethodResultsTest {

    @Test
    public void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final VerificationMethodResults results = VerificationMethodResults.builder()
                .id(id)
                .build();

        assertThat(results.getId()).isEqualTo(id);
    }

    @Test
    public void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final VerificationMethodResults results = VerificationMethodResults.builder()
                .contextId(contextId)
                .build();

        assertThat(results.getContextId()).isEqualTo(contextId);
    }

    @Test
    public void shouldReturnEmptyResultsByDefault() {
        final VerificationMethodResults results = VerificationMethodResults.builder()
                .build();

        assertThat(results).isEmpty();
    }

    @Test
    public void shouldReturnResults() {
        final Collection<VerificationMethodResult> methodResults = Collections.emptyList();

        final VerificationMethodResults results = VerificationMethodResults.builder()
                .results(methodResults)
                .build();

        assertThat(results).containsExactlyElementsOf(methodResults);
    }

    @Test
    public void shouldReturnEmptyOptionalIfResultsIsNull() {
        final VerificationMethodResults results = VerificationMethodResults.builder()
                .build();

        assertThat(results.getResult("methodName")).isEmpty();
    }

    @Test
    public void shouldReturnEmptyOptionalIfResultWithMethodNameNotPresent() {
        final VerificationMethodResult result = mock(VerificationMethodResult.class);
        given(result.getMethodName()).willReturn("methodName");

        final VerificationMethodResults results = VerificationMethodResults.builder()
                .results(Collections.singleton(result))
                .build();

        assertThat(results.getResult("anotherMethodName")).isEmpty();
    }

    @Test
    public void shouldReturnMethodResultIfResultWithMethodNameIsPresent() {
        final VerificationMethodResult result = mock(VerificationMethodResult.class);
        given(result.getMethodName()).willReturn("methodName");

        final VerificationMethodResults results = VerificationMethodResults.builder()
                .results(Collections.singleton(result))
                .build();

        assertThat(results.getResult("methodName")).contains(result);
    }

    @Test
    public void shouldAddResult() {
        final VerificationMethodResult result = mock(VerificationMethodResult.class);
        given(result.getMethodName()).willReturn("methodName");
        final UUID id = UUID.randomUUID();
        final UUID contextId = UUID.randomUUID();
        final VerificationMethodResults results = VerificationMethodResults.builder()
                .id(id)
                .contextId(contextId)
                .build();

        final VerificationMethodResults updatedResults = results.add(result);

        assertThat(updatedResults).containsExactly(result);
        assertThat(updatedResults.getId()).isEqualTo(id);
        assertThat(updatedResults.getContextId()).isEqualTo(contextId);
    }

}
