package uk.co.mruoc.idv.awslambda.authorizer.service;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultTokenRequestTest {

    @Test
    public void shouldReturnIdAsString() {
        final UUID id = UUID.randomUUID();

        final TokenRequest request = DefaultTokenRequest.builder()
                .id(id)
                .build();

        assertThat(request.getId()).isEqualTo(id.toString());
    }

    @Test
    public void shouldReturnSubject() {
        final String subject = "subject";

        final TokenRequest request = DefaultTokenRequest.builder()
                .subject(subject)
                .build();

        assertThat(request.getSubject()).isEqualTo(subject);
    }

    @Test
    public void shouldReturnEmptyOptionalIfTimeToLiveIsNotSet() {
        final TokenRequest request = DefaultTokenRequest.builder()
                .build();

        assertThat(request.getTimeToLiveInSeconds()).isEmpty();
    }

    @Test
    public void shouldReturnTimeToLive() {
        final long timeToLive = 60;

        final TokenRequest request = DefaultTokenRequest.builder()
                .timeToLiveInSeconds(timeToLive)
                .build();

        assertThat(request.getTimeToLiveInSeconds()).contains(timeToLive);
    }

}
