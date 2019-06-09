package uk.co.mruoc.idv.jsonapi.authorizer;

import org.junit.Test;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.authorizer.DefaultGenerateTokenRequest;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class GenerateTokenRequestDocumentTest {

    private static final String JSON = loadContentFromClasspath("/generate-token-request-document.json");
    private static final GenerateTokenRequestDocument DOCUMENT = buildDocument();

    private static final JsonConverter CONVERTER = new GenerateTokenJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocument() {
        final String json = CONVERTER.toJson(DOCUMENT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeDocument() {
        final GenerateTokenRequestDocument document = CONVERTER.toObject(JSON, GenerateTokenRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void shouldReturnRequest() {
        final GenerateTokenRequest request = buildRequest();

        final GenerateTokenRequestDocument document = new GenerateTokenRequestDocument(request);

        assertThat(document.getRequest()).isEqualTo(request);
    }

    private static GenerateTokenRequestDocument buildDocument() {
        final GenerateTokenRequest request = buildRequest();
        return new GenerateTokenRequestDocument(request);
    }

    private static GenerateTokenRequest buildRequest() {
        return DefaultGenerateTokenRequest.builder()
                .subject("subject")
                .validForSeconds(60L)
                .build();
    }


}
