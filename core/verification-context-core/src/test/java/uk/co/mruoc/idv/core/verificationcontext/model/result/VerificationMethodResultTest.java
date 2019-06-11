package uk.co.mruoc.idv.core.verificationcontext.model.result;

import org.junit.Test;
import uk.co.mruoc.idv.core.model.VerificationResult;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationMethodResultTest {

    @Test
    public void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final VerificationMethodResult result = VerificationMethodResult.builder()
                .contextId(contextId)
                .build();

        assertThat(result.getContextId()).isEqualTo(contextId);
    }

    @Test
    public void shouldReturnSequenceName() {
        final String sequenceName = "sequenceName";

        final VerificationMethodResult result = VerificationMethodResult.builder()
                .sequenceName(sequenceName)
                .build();

        assertThat(result.getSequenceName()).isEqualTo(sequenceName);
    }

    @Test
    public void shouldReturnMethodName() {
        final String methodName = "methodName";

        final VerificationMethodResult result = VerificationMethodResult.builder()
                .methodName(methodName)
                .build();

        assertThat(result.getMethodName()).isEqualTo(methodName);
    }

    @Test
    public void shouldReturnVerificationId() {
        final UUID verificationId = UUID.randomUUID();

        final VerificationMethodResult result = VerificationMethodResult.builder()
                .verificationId(verificationId)
                .build();

        assertThat(result.getVerificationId()).isEqualTo(verificationId);
    }

    @Test
    public void shouldReturnResult() {
        final String result = VerificationResult.SUCCESS;

        final VerificationMethodResult methodResult = VerificationMethodResult.builder()
                .result(result)
                .build();

        assertThat(methodResult.getResult()).isEqualTo(result);
    }

    @Test
    public void shouldBeSuccessfulIfResultIsSuccess() {
        final String result = VerificationResult.SUCCESS;

        final VerificationMethodResult methodResult = VerificationMethodResult.builder()
                .result(result)
                .build();

        assertThat(methodResult.isSuccessful()).isTrue();
    }

    @Test
    public void shouldNotBeSuccessfulIfResultIsNull() {
        final VerificationMethodResult methodResult = VerificationMethodResult.builder()
                .build();

        assertThat(methodResult.isSuccessful()).isFalse();
    }

    @Test
    public void shouldNotBeSuccessfulIfResultIsNotSuccess() {
        final String result = VerificationResult.FAILURE;

        final VerificationMethodResult methodResult = VerificationMethodResult.builder()
                .result(result)
                .build();

        assertThat(methodResult.isSuccessful()).isFalse();
    }

    @Test
    public void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final VerificationMethodResult result = VerificationMethodResult.builder()
                .timestamp(timestamp)
                .build();

        assertThat(result.getTimestamp()).isEqualTo(timestamp);
    }

}
