package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultService.MethodNotFoundInSequenceException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodNotFoundInSequenceExceptionTest {

    private final UUID contextId = UUID.fromString("eef9e103-d932-442c-adfd-d9ac1f47820f");
    private final String sequenceName = "sequenceName";
    private final String methodName = "methodName";

    private final MethodNotFoundInSequenceException exception = new MethodNotFoundInSequenceException(contextId, sequenceName, methodName);

    @Test
    public void shouldReturnMessage() {
        assertThat(exception.getMessage()).isEqualTo("method methodName not found in sequence sequenceName " +
                "in verification context eef9e103-d932-442c-adfd-d9ac1f47820f");
    }

    @Test
    public void shouldContextId() {
        assertThat(exception.getContextId()).isEqualTo(contextId);
    }

    @Test
    public void shouldReturnSequenceName() {
        assertThat(exception.getSequenceName()).isEqualTo(sequenceName);
    }

    @Test
    public void shouldReturnMethodName() {
        assertThat(exception.getMethodName()).isEqualTo(methodName);
    }

}
