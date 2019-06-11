package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.model.VerificationResult;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationAttemptTest {

    @Test
    public void shouldReturnChannelId() {
        final String channelId = "channelId";

        final VerificationAttempt attempt = VerificationAttempt.builder()
                .channelId(channelId)
                .build();

        assertThat(attempt.getChannelId()).isEqualTo(channelId);
    }

    @Test
    public void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final VerificationAttempt attempt = VerificationAttempt.builder()
                .timestamp(timestamp)
                .build();

        assertThat(attempt.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    public void shouldReturnAlias() {
        final Alias alias = new IdvIdAlias();

        final VerificationAttempt attempt = VerificationAttempt.builder()
                .alias(alias)
                .build();

        assertThat(attempt.getAlias()).isEqualTo(alias);
        assertThat(attempt.getAliasTypeName()).isEqualTo(alias.getTypeName());
    }

    @Test
    public void shouldReturnActivityType() {
        final String activityType = "activity";

        final VerificationAttempt attempt = VerificationAttempt.builder()
                .activityType(activityType)
                .build();

        assertThat(attempt.getActivityType()).isEqualTo(activityType);
    }

    @Test
    public void shouldReturnMethodName() {
        final String methodName = "methodName";

        final VerificationAttempt attempt = VerificationAttempt.builder()
                .methodName(methodName)
                .build();

        assertThat(attempt.getMethodName()).contains(methodName);
    }

    @Test
    public void shouldReturnIsSuccessfulFalseByDefault() {
        final VerificationAttempt attempt = VerificationAttempt.builder()
                .build();

        assertThat(attempt.isSuccessful()).isFalse();
    }

    @Test
    public void shouldReturnResult() {
        final String result = "result";

        final VerificationAttempt attempt = VerificationAttempt.builder()
                .result(result)
                .build();

        assertThat(attempt.getResult()).isEqualTo(result);
    }

    @Test
    public void shouldReturnIsSuccessfulIfResultIsSuccess() {
        final String result = VerificationResult.SUCCESS;

        final VerificationAttempt attempt = VerificationAttempt.builder()
                .result(result)
                .build();

        assertThat(attempt.isSuccessful()).isTrue();
    }

    @Test
    public void shouldReturnNotSuccessfulIfResultIsNotSuccess() {
        final String result = VerificationResult.FAILURE;

        final VerificationAttempt attempt = VerificationAttempt.builder()
                .result(result)
                .build();

        assertThat(attempt.isSuccessful()).isFalse();
    }

    @Test
    public void shouldPrintDetail() {
        final VerificationAttempt attempt = VerificationAttempt.builder()
                .build();

        assertThat(attempt.toString()).isEqualTo("VerificationAttempt(" +
                "channelId=null, timestamp=null, alias=null, activityType=null, " +
                "methodName=null, result=null)");
    }

}
