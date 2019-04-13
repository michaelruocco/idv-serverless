package uk.co.mruoc.idv.jsonapi.authorizer;

import org.junit.Test;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.json.authorizer.DefaultGenerateTokenRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultGenerateTokenRequestTest {

    @Test
    public void shouldReturnSubject() {
        final String subject = "subject";

        final GenerateTokenRequest request = DefaultGenerateTokenRequest.builder()
                .subject(subject)
                .build();

        assertThat(request.getSubject()).isEqualTo(subject);
    }

    @Test
    public void shouldReturnEmptyOptionalIfTimeToLiveIsNotSet() {
        final GenerateTokenRequest request = DefaultGenerateTokenRequest.builder()
                .build();

        assertThat(request.getTimeToLiveInSeconds()).isEmpty();
    }

    @Test
    public void shouldTimeToLiveInSeconds() {
        final long timeToLive = 60;

        final GenerateTokenRequest request = DefaultGenerateTokenRequest.builder()
                .validForSeconds(timeToLive)
                .build();

        assertThat(request.getTimeToLiveInSeconds()).contains(timeToLive);
    }

}
