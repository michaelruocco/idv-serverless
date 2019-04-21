package uk.co.mruoc.idv.core.verificationcontext.model.result;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationMethodResultTest {

    @Test
    public void shouldReturnVerificationContextId() {
        final UUID verificationContextId = UUID.randomUUID();

        final VerificationMethodResult result = VerificationMethodResult.builder()
                .verificationContextId(verificationContextId)
                .build();

        assertThat(result.getVerificationContextId()).isEqualTo(verificationContextId);
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
        final VerificationResult result = VerificationResult.SUCCESS;

        final VerificationMethodResult methodResult = VerificationMethodResult.builder()
                .result(result)
                .build();

        assertThat(methodResult.getResult()).isEqualTo(result);
    }

    @Test
    public void shouldBeSuccessfulIfResultIsSuccess() {
        final VerificationResult result = VerificationResult.SUCCESS;

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
        final VerificationResult result = VerificationResult.FAILURE;

        final VerificationMethodResult methodResult = VerificationMethodResult.builder()
                .result(result)
                .build();

        assertThat(methodResult.isSuccessful()).isFalse();
    }

}
