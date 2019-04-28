package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService.SequenceNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SequenceNotFoundExceptionTest {

    private final UUID contextId = UUID.fromString("eef9e103-d932-442c-adfd-d9ac1f47820f");
    private final String sequenceName = "sequenceName";

    private final SequenceNotFoundException exception = new SequenceNotFoundException(contextId, sequenceName);

    @Test
    public void shouldReturnMessage() {
        assertThat(exception.getMessage()).isEqualTo("sequence sequenceName not found " +
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

}
