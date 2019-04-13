package uk.co.mruoc.idv.json.authorizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultGenerateTokenRequestTest {

    @Test
    public void shouldReturnSubject() {
        final String subject = "subject";

        final DefaultGenerateTokenRequest request = DefaultGenerateTokenRequest.builder()
                .subject(subject)
                .build();

        assertThat(request.getSubject()).isEqualTo(subject);
    }

    @Test
    public void shouldReturnEmptyOptionalIfTimeToLiveIsNotSet() {
        final DefaultGenerateTokenRequest request = DefaultGenerateTokenRequest.builder()
                .build();

        assertThat(request.getTimeToLiveInSeconds()).isEmpty();
    }

    @Test
    public void shouldTimeToLiveInSeconds() {
        final long timeToLive = 60;

        final DefaultGenerateTokenRequest request = DefaultGenerateTokenRequest.builder()
                .validForSeconds(timeToLive)
                .build();

        assertThat(request.getTimeToLiveInSeconds()).contains(timeToLive);
    }

}
