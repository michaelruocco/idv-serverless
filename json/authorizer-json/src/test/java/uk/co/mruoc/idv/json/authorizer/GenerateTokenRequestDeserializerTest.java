package uk.co.mruoc.idv.json.authorizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class GenerateTokenRequestDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldDeserializeGenerateTokenRequestWithTimeToLive() throws IOException {
        final String json = loadContentFromClasspath("/generate-token-request-with-time-to-live.json");

        final GenerateTokenRequest request = MAPPER.readValue(json, GenerateTokenRequest.class);

        final GenerateTokenRequest expectedRequest = DefaultGenerateTokenRequest.builder()
                .subject("subject")
                .validForSeconds(60L)
                .build();
        assertThat(request).isEqualToComparingFieldByFieldRecursively(expectedRequest);
    }

    @Test
    public void shouldDeserializeGenerateTokenRequestWithoutTimeToLive() throws IOException {
        final String json = loadContentFromClasspath("/generate-token-request-without-time-to-live.json");

        final GenerateTokenRequest request = MAPPER.readValue(json, GenerateTokenRequest.class);

        final GenerateTokenRequest expectedRequest = DefaultGenerateTokenRequest.builder()
                .subject("subject")
                .build();
        assertThat(request).isEqualToComparingFieldByFieldRecursively(expectedRequest);
    }

}
