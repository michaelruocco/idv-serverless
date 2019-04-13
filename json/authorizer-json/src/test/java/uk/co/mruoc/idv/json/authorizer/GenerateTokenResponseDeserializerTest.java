package uk.co.mruoc.idv.json.authorizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.authorizer.model.DefaultTokenResponse;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class GenerateTokenResponseDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldDeserializeGenerateTokenRequestWithTimeToLive() throws IOException {
        final String json = loadContentFromClasspath("/generate-token-response.json");

        final TokenResponse response = MAPPER.readValue(json, TokenResponse.class);

        final TokenResponse expectedResponse = DefaultTokenResponse.builder()
                .token("token")
                .build();
        assertThat(response).isEqualToComparingFieldByFieldRecursively(expectedResponse);
    }

}
