package uk.co.mruoc.idv.core.verificationcontext.service.result;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationMethodResultConverterTest {

    private final VerificationContext context = mock(VerificationContext.class);
    private final VerificationMethodResult result = mock(VerificationMethodResult.class);
    private final VerificationMethodResults results = VerificationMethodResults.builder()
            .results(Collections.singleton(result))
            .build();

    private final VerificationMethodResultConverter converter = new VerificationMethodResultConverter();

    @Test
    public void shouldConvertActivityType() {
        final String activityType = "activityType";
        given(context.getActivityType()).willReturn(activityType);

        final VerificationAttempt attempt = converter.toAttempt(context, result);

        assertThat(attempt.getActivityType()).isEqualTo(activityType);
    }

    @Test
    public void shouldConvertAlias() {
        final Alias alias = new IdvIdAlias();
        given(context.getProvidedAlias()).willReturn(alias);

        final VerificationAttempt attempt = converter.toAttempt(context, result);

        assertThat(attempt.getAlias()).isEqualTo(alias);
        assertThat(attempt.getAliasTypeName()).isEqualTo(alias.getTypeName());
    }

    @Test
    public void shouldConvertChannelId() {
        final String channelId = "channelId";
        given(context.getChannelId()).willReturn(channelId);

        final VerificationAttempt attempt = converter.toAttempt(context, result);

        assertThat(attempt.getChannelId()).isEqualTo(channelId);
    }

    @Test
    public void shouldConvertMethodName() {
        final String methodName = "methodName";
        given(result.getMethodName()).willReturn(methodName);

        final VerificationAttempt attempt = converter.toAttempt(context, result);

        assertThat(attempt.getMethodName()).contains(methodName);
    }

    @Test
    public void shouldConvertTimestamp() {
        final Instant timestamp = Instant.now();
        given(result.getTimestamp()).willReturn(timestamp);

        final VerificationAttempt attempt = converter.toAttempt(context, result);

        assertThat(attempt.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    public void shouldConvertSuccessful() {
        final boolean successful = true;
        given(result.isSuccessful()).willReturn(successful);

        final VerificationAttempt attempt = converter.toAttempt(context, result);

        assertThat(attempt.isSuccessful()).isEqualTo(successful);
    }

    @Test
    public void shouldConvertVerificationId() {
        final UUID verificationId = UUID.randomUUID();
        given(result.getVerificationId()).willReturn(verificationId);

        final VerificationAttempt attempt = converter.toAttempt(context, result);

        assertThat(attempt.getVerificationId()).isEqualTo(verificationId);
    }

    @Test
    public void shouldConvertMultipleResults() {
        final Collection<VerificationAttempt> attempts = converter.toAttempts(context, results);

        assertThat(attempts).hasSize(1);
    }

}
