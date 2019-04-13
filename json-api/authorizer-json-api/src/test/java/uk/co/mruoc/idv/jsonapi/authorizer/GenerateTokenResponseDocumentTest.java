package uk.co.mruoc.idv.jsonapi.authorizer;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.authorizer.model.DefaultTokenResponse;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;
import uk.co.mruoc.idv.json.JsonConverter;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class GenerateTokenResponseDocumentTest {

    private static final UUID ID = UUID.fromString("a1a7da50-2ed3-4d22-8746-db18a8320d68");
    private static final String JSON = loadContentFromClasspath("/generate-token-response-document.json");
    private static final GenerateTokenResponseDocument DOCUMENT = buildDocument();

    private static final JsonConverter CONVERTER = new GenerateTokenJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocument() throws JSONException {
        final String json = CONVERTER.toJson(DOCUMENT);

        JSONAssert.assertEquals(JSON, json, JSONCompareMode.STRICT);
    }

    @Test
    public void shouldDeserializeDocument() {
        final GenerateTokenResponseDocument document = CONVERTER.toObject(JSON, GenerateTokenResponseDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void shouldReturnTokenResponse() {
        final TokenResponse response = buildResponse();

        final GenerateTokenResponseDocument document = new GenerateTokenResponseDocument(ID, response);

        assertThat(document.getTokenResponse()).isEqualTo(response);
    }

    @Test
    public void shouldReturnId() {
        final TokenResponse response = buildResponse();

        final GenerateTokenResponseDocument document = new GenerateTokenResponseDocument(ID, response);

        assertThat(document.getId()).isEqualTo(ID);
    }

    private static GenerateTokenResponseDocument buildDocument() {
        final TokenResponse response = buildResponse();
        return new GenerateTokenResponseDocument(ID, response);
    }

    private static TokenResponse buildResponse() {
        return DefaultTokenResponse.builder()
                .token("token")
                .build();
    }

}
