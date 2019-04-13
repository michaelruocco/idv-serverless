package uk.co.mruoc.idv.awslambda.authorizer.handler;

import org.junit.Test;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenRequest;
import uk.co.mruoc.idv.core.service.UuidGenerator;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GenerateTokenRequestConverterTest {

    private final UuidGenerator generator = mock(UuidGenerator.class);

    private final GenerateTokenRequestConverter converter = GenerateTokenRequestConverter.builder()
            .uuidGenerator(generator)
            .build();

    @Test
    public void shouldPopulateId() {
        final UUID id = UUID.randomUUID();
        final GenerateTokenRequest request = mock(GenerateTokenRequest.class);
        given(generator.randomUuid()).willReturn(id);
        given(request.getTimeToLiveInSeconds()).willReturn(Optional.empty());

        final TokenRequest tokenRequest = converter.toTokenRequest(request);

        assertThat(tokenRequest.getId()).contains(id.toString());
    }

    @Test
    public void shouldPopulateSubject() {
        final String subject = "subject";
        final GenerateTokenRequest request = mock(GenerateTokenRequest.class);
        given(request.getSubject()).willReturn(subject);
        given(request.getTimeToLiveInSeconds()).willReturn(Optional.empty());

        final TokenRequest tokenRequest = converter.toTokenRequest(request);

        assertThat(tokenRequest.getSubject()).contains(subject);
    }

    @Test
    public void shouldConvertToTokenRequestWithTimeToLiveIfTimeToLivePassed() {
        final long timeToLive = 60L;
        final GenerateTokenRequest request = mock(GenerateTokenRequest.class);
        given(request.getTimeToLiveInSeconds()).willReturn(Optional.of(timeToLive));

        final TokenRequest tokenRequest = converter.toTokenRequest(request);

        assertThat(tokenRequest.getTimeToLiveInSeconds()).contains(timeToLive);
    }

    @Test
    public void shouldConvertToTokenRequestWithoutTimeToLiveIfTimeToLiveNotPassed() {
        final GenerateTokenRequest request = mock(GenerateTokenRequest.class);
        given(request.getTimeToLiveInSeconds()).willReturn(Optional.empty());

        final TokenRequest tokenRequest = converter.toTokenRequest(request);

        assertThat(tokenRequest.getTimeToLiveInSeconds()).isEmpty();
    }

}
